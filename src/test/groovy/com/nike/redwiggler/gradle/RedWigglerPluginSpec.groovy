package com.nike.redwiggler.gradle

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.nio.file.Files

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class RedWigglerPluginSpec extends Specification {

    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def swaggerFile(String source) {
        Files.copy(getClass().getResourceAsStream(source), new File(testProjectDir.root, "swagger.yaml").toPath())
    }

    def javaSource(String source) {
        def java = testProjectDir.newFolder("src", "main", "java")
        Files.copy(getClass().getResourceAsStream(source), new File(java, source).toPath())
    }

    def "sane defaults swith swagger.yaml"() {
        given:
        swaggerFile "simpleSwagger.yaml"
        def requestsDir = testProjectDir.newFolder("build", "redwiggler-data")
        def outputFile = new File(new File(testProjectDir.root, "build"), "redwiggler.html")
        buildFile << """
            plugins {
                id 'com.nike.redwiggler'
            }
            repositories {
                jcenter()
            }
        """

        when:
        def result = GradleRunner.create()
                .withGradleVersion(gradleVersion)
                .withProjectDir(testProjectDir.root)
                .withArguments('runRedwigglerReport')
                .withPluginClasspath()
                .build()


        then:
        result.task(":runRedWigglerReport").outcome == SUCCESS
        outputFile.exists()

        where:
        gradleVersion << ['4.0', '4.1', '4.2']
    }

    def "build dependency with sane defaults"() {
        given:
        javaSource "TestJavaRestAssuredIntegration.java"
        buildFile << """
            plugins {
                id 'com.nike.redwiggler'
            }
            repositories {
                jcenter()
            }
            apply plugin: 'java'
            dependencies {
                compile redwiggler.dependency("restassured")
            }
        """

        when:
        def result = GradleRunner.create()
                .withGradleVersion(gradleVersion)
                .withProjectDir(testProjectDir.root)
                .withArguments('compileJava')
                .withPluginClasspath()
                .build()

        then:
        result.task(":compileJava").outcome == SUCCESS

        where:
        gradleVersion << ['4.0', '4.1', '4.2']
    }
}
