package com.nike.retail.common.plugins.gradle.redwiggler;

import com.nike.redwiggler.blueprint.ParseMdService;
import com.nike.redwiggler.core.APIValidator;
import com.nike.redwiggler.core.EndpointCallProvider;
import com.nike.redwiggler.core.EndpointSpecificationProvider;
import com.nike.redwiggler.core.GlobEndpointCallProvider;
import com.nike.redwiggler.core.models.ValidationResult;
import com.nike.redwiggler.html.HtmlOutputWriter;
import com.nike.redwiggler.swagger.SwaggerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RedWiggler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedWiggler.class);

    public static void validateResults(RedWigglerPluginExtension ext) throws IOException {

        EndpointSpecificationProvider provider = findEndpointSpecificationProvider(ext);

        EndpointCallProvider endpointCallProvider = new GlobEndpointCallProvider(ext.getDataDirectory(), "response.*.json");

        APIValidator validator = new APIValidator(endpointCallProvider, provider);

        List<ValidationResult> results = validator.validate();

        HtmlOutputWriter writer = new HtmlOutputWriter(results);
        writer.outputReport(ext.getOutput());

    }

    private static EndpointSpecificationProvider findEndpointSpecificationProvider(RedWigglerPluginExtension ext) {
        if (fileExists(ext.getSwaggerFile())) {
            LOGGER.info("Using Swagger to provide endpoint specification: " + ext.getSwaggerFile());
            return new SwaggerParser(ext.getSwaggerFile());
        } else if (fileExists(ext.getMarkdownFile())) {
            LOGGER.info("Using Blueprint to provide endpoint specification: " + ext.getMarkdownFile());
            return new ParseMdService(ext.getMarkdownFile());
        } else {
            throw new IllegalStateException("Did not find a valid API file (swagger or blueprint)");
        }
    }

    private static boolean fileExists(File file) {
        if (file == null) {
            return false;
        } else {
            return file.exists();
        }
    }

}
