package com.nike.redwiggler.gradle

import groovy.json.JsonSlurper

class BuildInfo {

    Object result

    BuildInfo() {
        def slurper = new JsonSlurper()
        def is = getClass().getResourceAsStream("/com/nike/redwiggler/gradle/build.json")
        result = slurper.parse(is as InputStream)
    }

    static BuildInfo INSTANCE = new BuildInfo()

    static String RED_WIGGLER_VERSION = INSTANCE.result.custom.redwigglerVersion
}
