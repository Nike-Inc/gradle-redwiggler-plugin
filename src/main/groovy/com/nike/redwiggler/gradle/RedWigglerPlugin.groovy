package com.nike.redwiggler.gradle

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
        project.tasks.create("runRedWigglerReport", RunRedWigglerReportTask)
    }
}
