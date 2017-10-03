package com.nike.redwiggler.gradle.tasks

import com.nike.redwiggler.gradle.RedWigglerPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class RedwigglerEndpointSpecificationProvider extends DefaultTask {

    def endpointSpecificationProvider

    @TaskAction
    void findEndpointSpecificationProvider() {
        RedWigglerPluginExtension ext = project.redwiggler
        def classloader = project.tasks.redwigglerGenerateClasspath.classLoader
        def SwaggerEndpointSpecificationProvider = classloader.loadClass("com.nike.redwiggler.swagger.SwaggerEndpointSpecificationProvider")
        endpointSpecificationProvider = SwaggerEndpointSpecificationProvider.apply(ext.swaggerFile)
    }
}
