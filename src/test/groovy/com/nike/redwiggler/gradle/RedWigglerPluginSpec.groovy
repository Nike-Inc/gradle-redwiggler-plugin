package com.nike.redwiggler.gradle

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.nio.file.Files

import static org.gradle.testkit.runner.TaskOutcome.*

/**
 * Copyright 2017-present, Nike, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 **/
class RedWigglerPluginSpec extends Specification {

    @Rule
    final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile

    static def gradleVersions = ['3.3', '3.4', '3.5', '4.0', '4.1', '4.2']

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def swaggerFile(String source, String target) {
        Files.copy(getClass().getResourceAsStream(source), new File(testProjectDir.root, target).toPath())
    }

    def javaSource(String source) {
        def java = testProjectDir.newFolder("src", "main", "java")
        Files.copy(getClass().getResourceAsStream(source), new File(java, source).toPath())
    }

    def "sane defaults with swagger.yaml"() {
        given:
        swaggerFile("simpleSwagger.yaml", "swagger.yaml")
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
                .withArguments('redwiggler', '--stacktrace')
                .withPluginClasspath()
                .build()


        then:
        result.task(":redwigglerSwaggerEndpointSpecificationProvider").outcome == SUCCESS
        result.task(":redwiggler").outcome == SUCCESS
        outputFile.exists()
        //shouldn't attach blueprint
        !result.task(":redwigglerBlueprintEndpointSpecificationProvider")

        where:
        gradleVersion << gradleVersions
    }

    def "custom swagger location"() {
        given:
        swaggerFile("simpleSwagger.yaml", "customSwaggerLocation.yaml")
        def requestsDir = testProjectDir.newFolder("build", "redwiggler-data")
        def outputFile = new File(new File(testProjectDir.root, "build"), "redwiggler.html")
        buildFile << """
            plugins {
                id 'com.nike.redwiggler'
            }
            repositories {
                jcenter()
            }
            redwiggler {
                swaggerFile new File(new File("${testProjectDir.root}"), "customSwaggerLocation.yaml")
            }
        """

        when:
        def result = GradleRunner.create()
                .withGradleVersion(gradleVersion)
                .withProjectDir(testProjectDir.root)
                .withArguments('redwiggler', '--stacktrace')
                .withPluginClasspath()
                .build()

        then:
        result.task(":redwigglerSwaggerEndpointSpecificationProvider").outcome == SUCCESS
        result.task(":redwiggler").outcome == SUCCESS
        outputFile.exists()
        //shouldn't attach blueprint
        !result.task(":redwigglerBlueprintEndpointSpecificationProvider")

        where:
        gradleVersion << gradleVersions
    }

    def "finalizedBy redwiggler "() {
        given:
        swaggerFile("simpleSwagger.yaml", "swagger.yaml")
        def requestsDir = testProjectDir.newFolder("build", "redwiggler-data")
        def outputFile = new File(new File(testProjectDir.root, "build"), "redwiggler.html")
        buildFile << """
            plugins {
                id 'com.nike.redwiggler'
            }
            repositories {
                jcenter()
            }
            task foo {
                finalizedBy tasks.redwiggler
            }
        """

        when:
        def result = GradleRunner.create()
                .withGradleVersion(gradleVersion)
                .withProjectDir(testProjectDir.root)
                .withArguments('foo', '--stacktrace')
                .withPluginClasspath()
                .build()

        then:
        result.task(":foo").outcome == UP_TO_DATE
        result.task(":redwiggler").outcome == SUCCESS

        where:
        gradleVersion << gradleVersions
    }

    def "add redwiggler module to classpath"() {
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
        gradleVersion << gradleVersions
    }
}
