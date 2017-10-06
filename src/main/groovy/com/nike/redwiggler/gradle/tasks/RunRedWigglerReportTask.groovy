package com.nike.redwiggler.gradle.tasks

import com.nike.redwiggler.gradle.RedWigglerPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Copyright 2017-present, Nike, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 **/
class RunRedWigglerReportTask extends DefaultTask {

    @TaskAction
    void generateReport() {
        RedWigglerPluginExtension ext = project.redwiggler

        def classloader = project.tasks.redwigglerGenerateClasspath.classLoader

        def redwiggler = classloader.loadClass("com.nike.redwiggler.core.Redwiggler")
        def GlobEndpointCallProvider = classloader.loadClass("com.nike.redwiggler.core.GlobEndpointCallProvider")
        def endpointSpecificationProvider = project.tasks.redwigglerEndpointSpecificationProvider.endpointSpecificationProvider
        def HtmlReportProcessor = classloader.loadClass("com.nike.redwiggler.html.HtmlReportProcessor")
        redwiggler.apply(
                GlobEndpointCallProvider.newInstance(ext.dataDirectory, ".*.json"),
                endpointSpecificationProvider,
                HtmlReportProcessor.newInstance(ext.output)
        )
    }

}
