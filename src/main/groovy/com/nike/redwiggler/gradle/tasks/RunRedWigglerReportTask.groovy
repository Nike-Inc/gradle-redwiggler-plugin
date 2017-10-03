package com.nike.redwiggler.gradle.tasks

import com.nike.redwiggler.gradle.RedWigglerPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class RunRedWigglerReportTask extends DefaultTask {

    @TaskAction
    void generateReport() {
        RedWigglerPluginExtension ext = project.redwiggler
        project.configurations {
            redwiggler
        }
        project.dependencies {
            redwiggler group: 'com.nike.redwiggler', name: 'redwiggler-swagger_2.12', version: ext.toolVersion
            redwiggler group: 'com.nike.redwiggler', name: 'redwiggler-reports-html_2.12', version: ext.toolVersion
            redwiggler group: 'org.slf4j', name: 'slf4j-simple', version: '1.7.25'
        }

        def resolvedConfiguration = project.configurations.redwiggler.resolvedConfiguration
        def files = resolvedConfiguration.files
        def classpath = files.collect{it.toURI().toURL()}
        def classloader = new URLClassLoader(classpath as URL[])

        def redwiggler = classloader.loadClass("com.nike.redwiggler.core.Redwiggler")
        def GlobEndpointCallProvider = classloader.loadClass("com.nike.redwiggler.core.GlobEndpointCallProvider")
        def SwaggerEndpointSpecificationProvider = classloader.loadClass("com.nike.redwiggler.swagger.SwaggerEndpointSpecificationProvider")
        def HtmlReportProcessor = classloader.loadClass("com.nike.redwiggler.html.HtmlReportProcessor")
        redwiggler.apply(
                GlobEndpointCallProvider.newInstance(ext.dataDirectory, ".*.json"),
                SwaggerEndpointSpecificationProvider.apply(ext.swaggerFile),
                HtmlReportProcessor.newInstance(ext.output)
        )
    }

}
