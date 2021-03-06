/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.plugin.formatter;

import com.intellij.formatting.*;
import com.intellij.formatting.alignment.AlignmentStrategy;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.formatter.FormatterUtil;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.kdoc.lexer.KDocTokens;
import org.jetbrains.jet.plugin.JetLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.jetbrains.jet.JetNodeTypes.*;
import static org.jetbrains.jet.lexer.JetTokens.*;

/**
 * @see Block for good JavaDoc documentation
 */
public class JetBlock extends AbstractBlock {
    private static final int KDOC_COMMENT_INDENT = 1;
    private final ASTAlignmentStrategy myAlignmentStrategy;
    private final Indent myIndent;
    private final CodeStyleSettings mySettings;
    private final KotlinSpacingBuilder mySpacingBuilder;

    private List<Block> mySubBlocks;

    private static final TokenSet CODE_BLOCKS = TokenSet.create(
            BLOCK,
            CLASS_BODY,
            FUNCTION_LITERAL);

    // private static final List<IndentWhitespaceRule>

    public JetBlock(@NotNull ASTNode node,
            ASTAlignmentStrategy alignmentStrategy,
            Indent indent,
            Wrap wrap,
            CodeStyleSettings settings,
            KotlinSpacingBuilder spacingBuilder
    ) {

        super(node, wrap, alignmentStrategy.getAlignment(node));
        myAlignmentStrategy = alignmentStrategy;
        myIndent = indent;
        mySettings = settings;
        mySpacingBuilder = spacingBuilder;
    }

    @Override
    public Indent getIndent() {
        return myIndent;
    }

    @Override
    protected List<Block> buildChildren() {
        if (mySubBlocks == null) {
            mySubBlocks = buildSubBlocks();
        }
        return new ArrayList<Block>(mySubBlocks);
    }

    private List<Block> buildSubBlocks() {
        List<Block> blocks = new ArrayList<Block>();

        ASTAlignmentStrategy childrenAlignmentStrategy = getChildrenAlignmentStrategy();

        for (ASTNode child = myNode.getFirstChildNode(); child != null; child = child.getTreeNext()) {
            IElementType childType = child.getElementType();

            if (child.getTextRange().getLength() == 0) continue;

            if (childType == TokenType.WHITE_SPACE) {
                continue;
            }

            blocks.add(buildSubBlock(child, childrenAlignmentStrategy));
        }
        return Collections.unmodifiableList(blocks);
    }

    @NotNull
    private Block buildSubBlock(@NotNull ASTNode child, ASTAlignmentStrategy alignmentStrategy) {
        Wrap wrap = null;

        // Affects to spaces around operators...
        if (child.getElementType() == OPERATION_REFERENCE) {
            ASTNode operationNode = child.getFirstChildNode();
            if (operationNode != null) {
                return new JetBlock(operationNode, alignmentStrategy, Indent.getNoneIndent(), wrap, mySettings, mySpacingBuilder);
            }
        }

        return new JetBlock(child, alignmentStrategy, createChildIndent(child), wrap, mySettings, mySpacingBuilder);
    }

    private static Indent indentIfNotBrace(@NotNull ASTNode child) {
        return child.getElementType() == RBRACE || child.getElementType() == LBRACE
               ? Indent.getNoneIndent()
               : Indent.getNormalIndent();
    }

    private static ASTNode getPrevWithoutWhitespace(ASTNode node) {
        node = node.getTreePrev();
        while (node != null && node.getElementType() == TokenType.WHITE_SPACE) {
            node = node.getTreePrev();
        }

        return node;
    }

    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return mySpacingBuilder.getSpacing(this, child1, child2);
    }

    @NotNull
    @Override
    public ChildAttributes getChildAttributes(int newChildIndex) {
        IElementType type = getNode().getElementType();
        if (CODE_BLOCKS.contains(type) ||
            type == WHEN ||
            type == IF ||
            type == FOR ||
            type == WHILE ||
            type == DO_WHILE) {

            return new ChildAttributes(Indent.getNormalIndent(), null);
        }
        else if (type == TRY) {
            // In try - try BLOCK catch BLOCK finally BLOCK
            return new ChildAttributes(Indent.getNoneIndent(), null);
        }
        else if (type == DOT_QUALIFIED_EXPRESSION) {
            return new ChildAttributes(Indent.getContinuationWithoutFirstIndent(), null);
        }
        else if (type == VALUE_PARAMETER_LIST || type == VALUE_ARGUMENT_LIST) {
            // Child index 1 - cursor is after ( - parameter alignment should be recreated
            // Child index 0 - before expression - know nothing about it
            if (newChildIndex != 1 && newChildIndex != 0 && newChildIndex < getSubBlocks().size()) {
                Block block = getSubBlocks().get(newChildIndex);
                return new ChildAttributes(block.getIndent(), block.getAlignment());
            }
            return new ChildAttributes(Indent.getContinuationIndent(), null);
        }
        else if (type == DOC_COMMENT) {
            return new ChildAttributes(Indent.getSpaceIndent(KDOC_COMMENT_INDENT), null);
        }

        if (isIncomplete()) {
            return super.getChildAttributes(newChildIndex);
        }

        return new ChildAttributes(Indent.getNoneIndent(), null);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }

    private ASTAlignmentStrategy getChildrenAlignmentStrategy() {
        CommonCodeStyleSettings jetCommonSettings = mySettings.getCommonSettings(JetLanguage.INSTANCE);
        JetCodeStyleSettings jetSettings = mySettings.getCustomSettings(JetCodeStyleSettings.class);

        // Prepare default null strategy
        ASTAlignmentStrategy strategy = myAlignmentStrategy;

        // Redefine list of strategies for some special elements
        IElementType parentType = myNode.getElementType();
        if (parentType == VALUE_PARAMETER_LIST) {
            strategy = getAlignmentForChildInParenthesis(
                    jetCommonSettings.ALIGN_MULTILINE_PARAMETERS, VALUE_PARAMETER, COMMA,
                    jetCommonSettings.ALIGN_MULTILINE_METHOD_BRACKETS, LPAR, RPAR);
        }
        else if (parentType == VALUE_ARGUMENT_LIST) {
            strategy = getAlignmentForChildInParenthesis(
                    jetCommonSettings.ALIGN_MULTILINE_PARAMETERS_IN_CALLS, VALUE_ARGUMENT, COMMA,
                    jetCommonSettings.ALIGN_MULTILINE_METHOD_BRACKETS, LPAR, RPAR);
        }
        else if (parentType == WHEN) {
            strategy = getAlignmentForCaseBranch(jetSettings.ALIGN_IN_COLUMNS_CASE_BRANCH);
        }
        return strategy;
    }

    private static ASTAlignmentStrategy getAlignmentForChildInParenthesis(
            boolean shouldAlignChild, final IElementType parameter, final IElementType delimiter,
            boolean shouldAlignParenthesis, final IElementType openParenth, final IElementType closeParenth
    ) {
        // TODO: Check this approach in other situations and refactor
        final Alignment parameterAlignment = shouldAlignChild ? Alignment.createAlignment() : null;
        final Alignment parenthesisAlignment = shouldAlignParenthesis ? Alignment.createAlignment() : null;

        return new ASTAlignmentStrategy() {
            @Override
            public Alignment getAlignment(@NotNull ASTNode node) {
                IElementType childNodeType = node.getElementType();

                ASTNode prev = getPrevWithoutWhitespace(node);
                if ((prev != null && prev.getElementType() == TokenType.ERROR_ELEMENT) || childNodeType == TokenType.ERROR_ELEMENT) {
                    return parameterAlignment;
                }

                if (childNodeType == openParenth || childNodeType == closeParenth) {
                    return parenthesisAlignment;
                }

                if (childNodeType == parameter || childNodeType == delimiter) {
                    return parameterAlignment;
                }

                return null;
            }
        };
    }

    private static ASTAlignmentStrategy getAlignmentForCaseBranch(boolean shouldAlignInColumns) {
        if (shouldAlignInColumns) {
            return ASTAlignmentStrategy
                    .fromTypes(AlignmentStrategy.createAlignmentPerTypeStrategy(Arrays.asList((IElementType) ARROW), WHEN_ENTRY, true));
        }
        else {
            return ASTAlignmentStrategy.getNullStrategy();
        }
    }

    static ASTIndentStrategy[] INDENT_RULES = new ASTIndentStrategy[] {
            ASTIndentStrategy.forNode("No indent for braces in blocks")
                    .in(BLOCK, CLASS_BODY, FUNCTION_LITERAL)
                    .forType(RBRACE, LBRACE)
                    .set(Indent.getNoneIndent()),

            ASTIndentStrategy.forNode("Indent for block content")
                    .in(BLOCK, CLASS_BODY, FUNCTION_LITERAL)
                    .notForType(RBRACE, LBRACE, BLOCK)
                    .set(Indent.getNormalIndent()),

            ASTIndentStrategy.forNode("Indent for property accessors")
                    .in(PROPERTY)
                    .forType(PROPERTY_ACCESSOR)
                    .set(Indent.getNormalIndent()),

            ASTIndentStrategy.forNode("For a single statement if 'for'")
                    .in(BODY)
                    .notForType(BLOCK)
                    .set(Indent.getNormalIndent()),

            ASTIndentStrategy.forNode("For the entry in when")
                    .forType(WHEN_ENTRY)
                    .set(Indent.getNormalIndent()),

            ASTIndentStrategy.forNode("For single statement in THEN and ELSE")
                    .in(THEN, ELSE)
                    .notForType(BLOCK)
                    .set(Indent.getNormalIndent()),

            ASTIndentStrategy.forNode("Indent for parts")
                    .in(PROPERTY, FUN)
                    .notForType(BLOCK, FUN_KEYWORD, VAL_KEYWORD, VAR_KEYWORD)
                    .set(Indent.getContinuationWithoutFirstIndent()),

            ASTIndentStrategy.forNode("KDoc comment indent")
                    .in(DOC_COMMENT)
                    .forType(KDocTokens.LEADING_ASTERISK, KDocTokens.END)
                    .set(Indent.getSpaceIndent(KDOC_COMMENT_INDENT)),

            ASTIndentStrategy.forNode("Block in when entry")
                    .in(WHEN_ENTRY)
                    .notForType(BLOCK, WHEN_CONDITION_EXPRESSION, WHEN_CONDITION_IN_RANGE, WHEN_CONDITION_IS_PATTERN, ELSE_KEYWORD, ARROW)
                    .set(Indent.getNormalIndent()),
    };

    @Nullable
    protected static Indent createChildIndent(@NotNull ASTNode child) {
        ASTNode childParent = child.getTreeParent();
        IElementType childType = child.getElementType();

        for (ASTIndentStrategy strategy : INDENT_RULES) {
            Indent indent = strategy.getIndent(child);
            if (indent != null) {
                return indent;
            }
        }

        // TODO: Try to rewrite other rules to declarative style
        if (childParent != null && childParent.getElementType() == DOT_QUALIFIED_EXPRESSION) {
            if (childParent.getFirstChildNode() != child && childParent.getLastChildNode() != child) {
                return Indent.getContinuationWithoutFirstIndent(false);
            }
        }

        if (childParent != null) {
            IElementType parentType = childParent.getElementType();

            if (parentType == VALUE_PARAMETER_LIST || parentType == VALUE_ARGUMENT_LIST) {
                ASTNode prev = getPrevWithoutWhitespace(child);
                if (childType == RPAR && (prev == null || prev.getElementType() != TokenType.ERROR_ELEMENT)) {
                    return Indent.getNoneIndent();
                }

                return Indent.getContinuationWithoutFirstIndent();
            }

            if (parentType == TYPE_PARAMETER_LIST || parentType == TYPE_ARGUMENT_LIST) {
                return Indent.getContinuationWithoutFirstIndent();
            }
        }

        return Indent.getNoneIndent();
    }
}
