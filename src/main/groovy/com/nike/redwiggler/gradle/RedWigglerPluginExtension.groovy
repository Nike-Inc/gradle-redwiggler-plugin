package com.nike.redwiggler.gradle

import org.gradle.api.Project

/**
 * Copyright 2017-present, Nike, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 **/
class RedWigglerPluginExtension {
    File swaggerFile
    File dataDirectory
    File output
    String toolVersion

    RedWigglerPluginExtension (Project project) {
        this.output = new File(project.getBuildDir(), "redwiggler.html")
        this.dataDirectory = new File(project.getBuildDir(), "redwiggler-data")
        this.swaggerFile = new File(project.rootDir, "swagger.yaml")
        this.toolVersion = "0.5.4"
    }

    def dependency(String name) {
        return [group: 'com.nike.redwiggler', name: 'redwiggler-' + name + '_2.12', version: toolVersion]
    }
}

