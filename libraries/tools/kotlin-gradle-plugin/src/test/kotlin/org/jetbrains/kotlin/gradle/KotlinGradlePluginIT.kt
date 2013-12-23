package org.jetbrains.kotlin.gradle

import com.google.common.io.Files
import com.intellij.openapi.util.SystemInfo
import java.io.File
import java.util.Arrays
import java.util.Scanner
import org.junit.Before
import org.junit.After
import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import kotlin.test.fail

class BasicKotlinGradleIT {

    var workingDir: File = File(".")

    Before fun setUp() {
        workingDir = Files.createTempDir()
        workingDir.mkdirs()
    }

    After fun tearDown() {
        deleteRecursively(workingDir)
    }

    Test fun testCrossCompile() {
        val projectName = "alfa"
        val projectDir = File(workingDir, projectName)

        // Run the build first time
        val (buildOutput, resultCode) = buildProject(projectName, "compileDeployKotlin")
        assertSuccessful(resultCode)
        assertContains(buildOutput, ":compileKotlin")
        assertContains(buildOutput, ":compileTestKotlin")
        assertContains(buildOutput, ":compileDeployKotlin")
        assertReportExists(projectDir)

        // Run the build second time, assert everything is up-to-date
        val (up2dateBuildOutput, up2dateResult) = buildProject(projectName, "compileDeployKotlin")
        assertSuccessful(up2dateResult)
        assertContains(up2dateBuildOutput, ":compileKotlin UP-TO-DATE")
        assertContains(up2dateBuildOutput, ":compileTestKotlin UP-TO-DATE")
        assertContains(up2dateBuildOutput, ":compileDeployKotlin UP-TO-DATE")
        assertContains(up2dateBuildOutput, ":compileJava UP-TO-DATE")
    }

    Test fun testKotlinOnlyCompile() {
        val projectName = "beta"
        val projectDir = File(workingDir, projectName)

        // Run the build first time
        val (buildOutput, resultCode) = buildProject(projectName, "build")
        assertSuccessful(resultCode)
        assertContains(buildOutput, ":compileKotlin")
        assertContains(buildOutput, ":compileTestKotlin")
        assertReportExists(projetDir)

        // Run the build second time, assert everything is up-to-date
        val (up2dateBuildOutput, up2dateResult) = buildProject(projectName, "build")
        assertSuccessful(up2dateResult)
        assertContains(up2dateBuildOutput, ":compileKotlin UP-TO-DATE")
        assertContains(up2dateBuildOutput, ":compileTestKotlin UP-TO-DATE")
    }

    Test fun testKotlinClasspath() {
        val projectName = "classpathTest"
        val projectDir = File(workingDir, projectName)

        val (buildOutput, resultCode) = buildProject(projectName, "build")
        assertSuccessful(resultCode)
        assertContains(buildOutput, ":compileKotlin")
        assertContains(buildOutput, ":compileTestKotlin")
        assertReportExists(projetDir)
    }

    private fun assertSuccessful(resultCode: Int) {
        assertSuccessful(resultCode)
    }

    private fun assertContains(output: String, expected: String) {
        assertTrue(output.contains(expected), "Should contain '$expected', actual output: $output")
    }

    private fun assertReportExists(projectDir: File) {
        assertTrue(File(projectDir, "build/reports/tests/demo.TestSource.html").exists(), "Test report does not exist. Were tests executed?")
    }

    private fun buildProject(path: String, commandName: String): Pair<String, Int> {
        copyRecursively(File("src/test/resources/testProject/$path"), workingDir)
        val projectDir = File(workingDir, path)

        val cmd = createCommand(commandName)

        val process = createProcess(cmd, projectDir)
        return readOutput(process)
    }

    private fun createCommand(name: String): List<String> {
        val pathToKotlinPlugin = "-PpathToKotlinPlugin=" + File("local-repo").getAbsolutePath()

        return if (SystemInfo.isWindows)
            listOf("cmd", "/C", "gradlew.bat", name, "build", pathToKotlinPlugin, "--no-daemon", "--debug")
        else
            listOf("/bin/bash", "./gradlew", name, "build", pathToKotlinPlugin, "--no-daemon", "--debug")
    }

    private fun createProcess(cmd: List<String>, projectDir: File): Process {
        val builder = ProcessBuilder(cmd)
        builder.directory(projectDir)
        builder.redirectErrorStream(true)
        return builder.start()
    }

    private fun readOutput(process: Process): Pair<String, Int> {
        val s = Scanner(process.getInputStream()!!)
        val text = StringBuilder()
        while (s.hasNextLine()) {
            text append s.nextLine()
            text append "\n"
        }
        s.close()

        val result = process.waitFor()
        return text.toString() to result
    }

    fun copyRecursively(source: File, target: File) {
        assertTrue(target.isDirectory())
        val targetFile = File(target, source.getName())
        if (source.isDirectory()) {
            targetFile.mkdir()
            val array = source.listFiles()
            if (array != null) {
                for (child in array) {
                    copyRecursively(child, targetFile)
                }
            }
        } else {
            Files.copy(source, targetFile)
        }
    }


    fun deleteRecursively(f: File): Unit {
        if (f.isDirectory()) {
            val children = f.listFiles()
            if (children != null) {
                for (child in children) {
                    deleteRecursively(child)
                }
            }
            val shouldBeEmpty = f.listFiles()
            if (shouldBeEmpty != null) {
                assertTrue(shouldBeEmpty.isEmpty())
            } else {
                fail("Error listing directory content")
            }
        }
        f.delete()
    }
}