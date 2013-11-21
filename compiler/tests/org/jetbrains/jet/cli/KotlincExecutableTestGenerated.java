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

package org.jetbrains.jet.cli;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.cli.AbstractKotlincExecutableTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.GenerateTests}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@InnerTestClasses({KotlincExecutableTestGenerated.Jvm.class, KotlincExecutableTestGenerated.Js.class})
public class KotlincExecutableTestGenerated extends AbstractKotlincExecutableTest {
    @TestMetadata("compiler/testData/cli/jvm")
    @InnerTestClasses({Jvm.WrongAbiVersionLib.class})
    public static class Jvm extends AbstractKotlincExecutableTest {
        public void testAllFilesPresentInJvm() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("compiler/testData/cli/jvm"), Pattern.compile("^(.+)\\.args$"), true);
        }
        
        @TestMetadata("diagnosticsOrder.args")
        public void testDiagnosticsOrder() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/diagnosticsOrder.args");
        }
        
        @TestMetadata("help.args")
        public void testHelp() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/help.args");
        }
        
        @TestMetadata("multipleTextRangesInDiagnosticsOrder.args")
        public void testMultipleTextRangesInDiagnosticsOrder() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/multipleTextRangesInDiagnosticsOrder.args");
        }
        
        @TestMetadata("nonExistingClassPathAndAnnotationsPath.args")
        public void testNonExistingClassPathAndAnnotationsPath() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/nonExistingClassPathAndAnnotationsPath.args");
        }
        
        @TestMetadata("nonExistingSourcePath.args")
        public void testNonExistingSourcePath() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/nonExistingSourcePath.args");
        }
        
        @TestMetadata("printArguments.args")
        public void testPrintArguments() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/printArguments.args");
        }
        
        @TestMetadata("script.args")
        public void testScript() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/script.args");
        }
        
        @TestMetadata("simple.args")
        public void testSimple() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/simple.args");
        }
        
        @TestMetadata("suppressAllWarningsLowercase.args")
        public void testSuppressAllWarningsLowercase() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/suppressAllWarningsLowercase.args");
        }
        
        @TestMetadata("suppressAllWarningsMixedCase.args")
        public void testSuppressAllWarningsMixedCase() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/suppressAllWarningsMixedCase.args");
        }
        
        @TestMetadata("wrongAbiVersion.args")
        public void testWrongAbiVersion() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/wrongAbiVersion.args");
        }
        
        @TestMetadata("wrongArgument.args")
        public void testWrongArgument() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/wrongArgument.args");
        }
        
        @TestMetadata("wrongKotlinSignature.args")
        public void testWrongKotlinSignature() throws Exception {
            doJvmTest("compiler/testData/cli/jvm/wrongKotlinSignature.args");
        }
        
        @TestMetadata("compiler/testData/cli/jvm/wrongAbiVersionLib")
        @InnerTestClasses({})
        public static class WrongAbiVersionLib extends AbstractKotlincExecutableTest {
            public void testAllFilesPresentInWrongAbiVersionLib() throws Exception {
                JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("compiler/testData/cli/jvm/wrongAbiVersionLib"), Pattern.compile("^(.+)\\.args$"), true);
            }
            
            public static Test innerSuite() {
                TestSuite suite = new TestSuite("WrongAbiVersionLib");
                suite.addTestSuite(WrongAbiVersionLib.class);
                return suite;
            }
        }
        
        public static Test innerSuite() {
            TestSuite suite = new TestSuite("Jvm");
            suite.addTestSuite(Jvm.class);
            suite.addTest(WrongAbiVersionLib.innerSuite());
            return suite;
        }
    }
    
    @TestMetadata("compiler/testData/cli/js")
    public static class Js extends AbstractKotlincExecutableTest {
        public void testAllFilesPresentInJs() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("compiler/testData/cli/js"), Pattern.compile("^(.+)\\.args$"), true);
        }
        
        @TestMetadata("outputPostfixFileNotFound.args")
        public void testOutputPostfixFileNotFound() throws Exception {
            doJsTest("compiler/testData/cli/js/outputPostfixFileNotFound.args");
        }
        
        @TestMetadata("outputPrefixFileNotFound.args")
        public void testOutputPrefixFileNotFound() throws Exception {
            doJsTest("compiler/testData/cli/js/outputPrefixFileNotFound.args");
        }
        
        @TestMetadata("printArgumentsWithManyValue.args")
        public void testPrintArgumentsWithManyValue() throws Exception {
            doJsTest("compiler/testData/cli/js/printArgumentsWithManyValue.args");
        }
        
        @TestMetadata("simple2js.args")
        public void testSimple2js() throws Exception {
            doJsTest("compiler/testData/cli/js/simple2js.args");
        }
        
        @TestMetadata("suppressAllWarningsJS.args")
        public void testSuppressAllWarningsJS() throws Exception {
            doJsTest("compiler/testData/cli/js/suppressAllWarningsJS.args");
        }
        
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("KotlincExecutableTestGenerated");
        suite.addTest(Jvm.innerSuite());
        suite.addTestSuite(Js.class);
        return suite;
    }
}