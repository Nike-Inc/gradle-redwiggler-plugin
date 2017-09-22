package com.nike.redwiggler.gradle

import com.nike.redwiggler.core.GlobEndpointCallProvider
import com.nike.redwiggler.core.Redwiggler
import com.nike.redwiggler.html.HtmlReportProcessor
import com.nike.redwiggler.swagger.SwaggerEndpointSpecificationProvider
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
            RedWigglerPluginExtension ext = project.redwiggler
            Redwiggler.apply(
                    new GlobEndpointCallProvider(ext.dataDirectory, ".*.json"),
                    new SwaggerEndpointSpecificationProvider(ext.swaggerFile),
                    new HtmlReportProcessor(ext.output)

            )
            ResultValidator.validateResults(ext)
        }
    }
}

class RedWigglerPluginExtension {
    File swaggerFile
    File dataDirectory
    File output

    RedWigglerPluginExtension (Project project) {
        this.output = new File(project.getBuildDir(), "redwiggler.html")
        this.dataDirectory = new File(new File(project.getBuildDir(), "reports"), "tests")
        this.swaggerFile = new File(project.rootDir, "swagger.yaml")
    }
}
