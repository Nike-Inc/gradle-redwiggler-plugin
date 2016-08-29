package com.nike.retail.common.plugins.gradle.redwiggler

import com.nike.retail.redwiggler.parse.APIReader
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RedWigglerPlugin implements Plugin<Project> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedWigglerPlugin)

    @Override
    void apply(Project project) {
        LOGGER.info('Running wiggler')
        project.extensions.create('redwiggler', RedWigglerPluginExtension)
        project.task('runRedWigglerReport') << {
            APIReader reader = new APIReader("${project.redwiggler.markdownFile}", "${project.redwiggler.dataDirectory}");
            reader.validate();
            reader.outputReport("${project.redwiggler.output}");
        }
    }
}

class RedWigglerPluginExtension {
    String markdownFile
    String dataDirectory
    String output
}

