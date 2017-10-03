# RedWiggler contract testing plugin

[![Build Status](https://travis-ci.com/Nike-Inc/gradle-redwiggler-plugin.svg?token=PmECSWCH8LFEKNdzr64F&branch=master)](https://travis-ci.com/Nike-Inc/gradle-redwiggler-plugin)

## Overview

Plugin to integrate [RedWiggler](https://github.com/Nike-Inc/redwiggler) in a gradle build.

## Requirements

This plugin requires gradle 3.3 or greater.

## Add to the [Classpath](https://docs.gradle.org/current/userguide/organizing_build_logic.html) of Gradle Build Script
Add the library `'redwiggler-gradle-plugin'` to the classpath of gradle build script

    dependencies{
        classpath 'com.nike.redwiggler.gradle:gradle-redwiggler-plugin:<version>'
    }
    
## Apply Plugin
    
    apply plugin: 'com.nike.redwiggler'

## Provided Tasks

The plugin adds the following task:

+ redwiggler: runs the contract test report based on the locations of the markdown and test results
+ redwigglerGenerateClasspath: generated the classpath for redwiggler to run
+ redwigglerEndpointSpecificationProvider: creates the endpoint specification provider

## Configurations
    
The configuration block is called "redwiggler" and accepts the following configurations:

|Property Name   	| Description |Default Value  	|
|---	|---	| --- |
| swaggerFile | The location of the swagger.yaml file. | ./swagger.yaml |
| dataDirectory   	| The directory where test results are logged. | build/redwiggler-data |
| output   	| The directory where the test report should be saved. | build/redwiggler.html |
| toolVersion | The version of RedWiggler to use. | 0.5.1 |

# Releasing

To release, tag version and version to new version. To release to bintray:

```shell
./gradlew bintrayUpload
```
