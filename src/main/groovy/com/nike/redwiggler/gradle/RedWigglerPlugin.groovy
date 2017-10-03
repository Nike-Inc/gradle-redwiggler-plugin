package com.nike.redwiggler.gradle

import com.nike.redwiggler.gradle.tasks.RedWigglerClasspathTask
import com.nike.redwiggler.gradle.tasks.RedwigglerEndpointSpecificationProvider
import com.nike.redwiggler.gradle.tasks.RunRedWigglerReportTask
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
        project.tasks.create("redwigglerGenerateClasspath", RedWigglerClasspathTask)
        project.tasks.create("redwigglerEndpointSpecificationProvider", RedwigglerEndpointSpecificationProvider)
                .dependsOn("redwigglerGenerateClasspath")
        project.tasks.create("redwiggler", RunRedWigglerReportTask)
                .dependsOn("redwigglerGenerateClasspath", "redwigglerEndpointSpecificationProvider")

        project.afterEvaluate {
            RedWigglerPluginExtension ext = project.extensions.redwiggler
            project.configurations {
                redwiggler
            }
            project.dependencies {
                redwiggler group: 'com.nike.redwiggler', name: 'redwiggler-swagger_2.12', version: ext.toolVersion
                redwiggler group: 'com.nike.redwiggler', name: 'redwiggler-reports-html_2.12', version: ext.toolVersion
                redwiggler group: 'org.slf4j', name: 'slf4j-simple', version: '1.7.25'
            }
        }
    }
}
