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
class SwaggerEndpointSpecificationProviderTask extends EndpointSpecificationTask {

    @TaskAction
    void findEndpointSpecificationProvider() {
        RedWigglerPluginExtension ext = project.redwiggler
        def classloader = project.tasks.redwigglerGenerateClasspath.classLoader
        def SwaggerEndpointSpecificationProvider = classloader.loadClass("com.nike.redwiggler.swagger.SwaggerEndpointSpecificationProvider")
        endpointSpecificationProvider = SwaggerEndpointSpecificationProvider.apply(ext.swaggerFile)
    }
}
