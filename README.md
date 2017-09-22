# RedWiggler contract testing plugin

https://travis-ci.com/Nike-Inc/gradle-redwiggler-plugin.svg?token=PmECSWCH8LFEKNdzr64F&branch=master

## Add to the [Classpath](https://docs.gradle.org/current/userguide/organizing_build_logic.html) of Gradle Build Script
Add the library `'redwiggler-gradle-plugin'` to the classpath of gradle build script

    dependencies{
        classpath 'com.nike.retail:redwiggler-gradle-plugin:1.+'
    }
    
Please configure correct repositories as well.
    
## Apply Plugin
    
    apply plugin: 'com.nike.retail.common.plugins.gradle.redwiggler'

## Provided Tasks

The plugin adds the following task:

+ runRedWigglerReport : runs the contract test report based on the locations of the markdown and test results

## Configurations
    
The configuration block is called "redwiggler" and accepts the following configurations:

|Property Name   	| Description |Default Value  	|
|---	|---	| --- |
| markdownFile   	| The location of the API.md file. |
| dataDirectory   	| The directory where test results are logged. |
| output   	| The directory where the test report should be saved. |
