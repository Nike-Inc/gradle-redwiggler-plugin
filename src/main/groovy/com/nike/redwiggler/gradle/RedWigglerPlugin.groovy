package com.nike.redwiggler.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RedWigglerPlugin implements Plugin<Project> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedWigglerPlugin)

    @Override
    void apply(Project project) {
        LOGGER.info('Running wiggler')

        project.extensions.create('redwiggler', RedWigglerPluginExtension, project)
        project.task('runRedWigglerReport') doLast {
            RedWigglerPluginExtension ext = project.redwiggler
            project.configurations {
                redwiggler
            }
            project.dependencies {
                redwiggler group: 'com.nike.redwiggler', name: 'redwiggler-swagger_2.12', version: ext.toolVersion
                redwiggler group: 'com.nike.redwiggler', name: 'redwiggler-reports-html_2.12', version: ext.toolVersion
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
}

class RedWigglerPluginExtension {
    File swaggerFile
    File dataDirectory
    File output
    String toolVersion

    RedWigglerPluginExtension (Project project) {
        this.output = new File(project.getBuildDir(), "redwiggler.html")
        this.dataDirectory = new File(project.getBuildDir(), "redwiggler-data")
        this.swaggerFile = new File(project.rootDir, "swagger.yaml")
        this.toolVersion = "0.5.1"
    }

    def dependency(String name) {
        return [group: 'com.nike.redwiggler', name: 'redwiggler-' + name + '_2.12', version: toolVersion]
    }
}
