package com.nike.retail.common.plugins.gradle.redwiggler

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
        project.task('runRedWigglerReport') << {
            RedWigglerPluginExtension ext = project.redwiggler;
            RedWiggler.validateResultsAgainstMarkdown(
                    ext.markdownFile,
                    ext.dataDirectory,
                    ext.output
            )
        }
    }
}

class RedWigglerPluginExtension {
    File markdownFile
    File dataDirectory
    File output

    RedWigglerPluginExtension (Project project) {
        this.output = new File(project.getBuildDir(), "redwiggler.html")
        this.dataDirectory = new File(new File(project.getBuildDir(), "reports"), "tests")
        this.markdownFile = new File(project.rootDir, "API.md")
    }
}
