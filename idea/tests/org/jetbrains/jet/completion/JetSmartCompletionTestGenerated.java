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

package org.jetbrains.jet.completion;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.completion.AbstractJvmSmartCompletionTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.GenerateTests}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/completion/smart")
public class JetSmartCompletionTestGenerated extends AbstractJvmSmartCompletionTest {
    public void testAllFilesPresentInSmart() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/completion/smart"), Pattern.compile("^(.+)\\.kt$"), true);
    }
    
    @TestMetadata("AutoCastedType.kt")
    public void testAutoCastedType() throws Exception {
        doTest("idea/testData/completion/smart/AutoCastedType.kt");
    }
    
    @TestMetadata("AutoCastedTypeWithQualifier.kt")
    public void testAutoCastedTypeWithQualifier() throws Exception {
        doTest("idea/testData/completion/smart/AutoCastedTypeWithQualifier.kt");
    }
    
    @TestMetadata("AutoNotNullType.kt")
    public void testAutoNotNullType() throws Exception {
        doTest("idea/testData/completion/smart/AutoNotNullType.kt");
    }
    
    @TestMetadata("AutoNotNullTypeWithQualifier.kt")
    public void testAutoNotNullTypeWithQualifier() throws Exception {
        doTest("idea/testData/completion/smart/AutoNotNullTypeWithQualifier.kt");
    }
    
    @TestMetadata("ChainedCall.kt")
    public void testChainedCall() throws Exception {
        doTest("idea/testData/completion/smart/ChainedCall.kt");
    }
    
    @TestMetadata("ClassObjectMembers.kt")
    public void testClassObjectMembers() throws Exception {
        doTest("idea/testData/completion/smart/ClassObjectMembers.kt");
    }
    
    @TestMetadata("ClassObjectMembersForNullable.kt")
    public void testClassObjectMembersForNullable() throws Exception {
        doTest("idea/testData/completion/smart/ClassObjectMembersForNullable.kt");
    }
    
    @TestMetadata("Constructor.kt")
    public void testConstructor() throws Exception {
        doTest("idea/testData/completion/smart/Constructor.kt");
    }
    
    @TestMetadata("ConstructorForGenericType.kt")
    public void testConstructorForGenericType() throws Exception {
        doTest("idea/testData/completion/smart/ConstructorForGenericType.kt");
    }
    
    @TestMetadata("ConstructorForNullable.kt")
    public void testConstructorForNullable() throws Exception {
        doTest("idea/testData/completion/smart/ConstructorForNullable.kt");
    }
    
    @TestMetadata("EmptyPrefix.kt")
    public void testEmptyPrefix() throws Exception {
        doTest("idea/testData/completion/smart/EmptyPrefix.kt");
    }
    
    @TestMetadata("EnumMembers.kt")
    public void testEnumMembers() throws Exception {
        doTest("idea/testData/completion/smart/EnumMembers.kt");
    }
    
    @TestMetadata("InsideIdentifier.kt")
    public void testInsideIdentifier() throws Exception {
        doTest("idea/testData/completion/smart/InsideIdentifier.kt");
    }
    
    @TestMetadata("JavaEnumMembers.kt")
    public void testJavaEnumMembers() throws Exception {
        doTest("idea/testData/completion/smart/JavaEnumMembers.kt");
    }
    
    @TestMetadata("JavaEnumMembersForNullable.kt")
    public void testJavaEnumMembersForNullable() throws Exception {
        doTest("idea/testData/completion/smart/JavaEnumMembersForNullable.kt");
    }
    
    @TestMetadata("JavaStaticFields.kt")
    public void testJavaStaticFields() throws Exception {
        doTest("idea/testData/completion/smart/JavaStaticFields.kt");
    }
    
    @TestMetadata("JavaStaticFieldsForNullable.kt")
    public void testJavaStaticFieldsForNullable() throws Exception {
        doTest("idea/testData/completion/smart/JavaStaticFieldsForNullable.kt");
    }
    
    @TestMetadata("JavaStaticMethods.kt")
    public void testJavaStaticMethods() throws Exception {
        doTest("idea/testData/completion/smart/JavaStaticMethods.kt");
    }
    
    @TestMetadata("MethodCallArgument.kt")
    public void testMethodCallArgument() throws Exception {
        doTest("idea/testData/completion/smart/MethodCallArgument.kt");
    }
    
    @TestMetadata("NoConstructorForAbstract.kt")
    public void testNoConstructorForAbstract() throws Exception {
        doTest("idea/testData/completion/smart/NoConstructorForAbstract.kt");
    }
    
    @TestMetadata("NoConstructorForJavaInterface.kt")
    public void testNoConstructorForJavaInterface() throws Exception {
        doTest("idea/testData/completion/smart/NoConstructorForJavaInterface.kt");
    }
    
    @TestMetadata("NoConstructorForTrait.kt")
    public void testNoConstructorForTrait() throws Exception {
        doTest("idea/testData/completion/smart/NoConstructorForTrait.kt");
    }
    
    @TestMetadata("NoConstructorWithQualifier.kt")
    public void testNoConstructorWithQualifier() throws Exception {
        doTest("idea/testData/completion/smart/NoConstructorWithQualifier.kt");
    }
    
    @TestMetadata("NoNothing.kt")
    public void testNoNothing() throws Exception {
        doTest("idea/testData/completion/smart/NoNothing.kt");
    }
    
    @TestMetadata("NoSillyAssignment.kt")
    public void testNoSillyAssignment() throws Exception {
        doTest("idea/testData/completion/smart/NoSillyAssignment.kt");
    }
    
    @TestMetadata("NotSillyAssignment.kt")
    public void testNotSillyAssignment() throws Exception {
        doTest("idea/testData/completion/smart/NotSillyAssignment.kt");
    }
    
    @TestMetadata("QualifiedThis.kt")
    public void testQualifiedThis() throws Exception {
        doTest("idea/testData/completion/smart/QualifiedThis.kt");
    }
    
    @TestMetadata("QualifiedThisOfAnonymousObject.kt")
    public void testQualifiedThisOfAnonymousObject() throws Exception {
        doTest("idea/testData/completion/smart/QualifiedThisOfAnonymousObject.kt");
    }
    
    @TestMetadata("QualifiedThisOfExtensionFunction.kt")
    public void testQualifiedThisOfExtensionFunction() throws Exception {
        doTest("idea/testData/completion/smart/QualifiedThisOfExtensionFunction.kt");
    }
    
    @TestMetadata("QualifiedThisOfExtensionLambda1.kt")
    public void testQualifiedThisOfExtensionLambda1() throws Exception {
        doTest("idea/testData/completion/smart/QualifiedThisOfExtensionLambda1.kt");
    }
    
    @TestMetadata("QualifiedThisOfExtensionLambda2.kt")
    public void testQualifiedThisOfExtensionLambda2() throws Exception {
        doTest("idea/testData/completion/smart/QualifiedThisOfExtensionLambda2.kt");
    }
    
    @TestMetadata("QualifiedThisOfExtensionLambda3.kt")
    public void testQualifiedThisOfExtensionLambda3() throws Exception {
        doTest("idea/testData/completion/smart/QualifiedThisOfExtensionLambda3.kt");
    }
    
    @TestMetadata("This.kt")
    public void testThis() throws Exception {
        doTest("idea/testData/completion/smart/This.kt");
    }
    
    @TestMetadata("VariableInitializer.kt")
    public void testVariableInitializer() throws Exception {
        doTest("idea/testData/completion/smart/VariableInitializer.kt");
    }
    
    @TestMetadata("WithPrefix.kt")
    public void testWithPrefix() throws Exception {
        doTest("idea/testData/completion/smart/WithPrefix.kt");
    }
    
    @TestMetadata("WithQualifier.kt")
    public void testWithQualifier() throws Exception {
        doTest("idea/testData/completion/smart/WithQualifier.kt");
    }
    
}
