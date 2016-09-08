package com.nike.retail.common.plugins.gradle.redwiggler;

import com.nike.redwiggler.blueprint.ParseMdService;
import com.nike.redwiggler.core.APIValidator;
import com.nike.redwiggler.core.EndpointCallProvider;
import com.nike.redwiggler.core.GlobEndpointCallProvider;
import com.nike.redwiggler.core.models.ValidationResult;
import com.nike.redwiggler.html.HtmlOutputWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RedWiggler {
    public static List<ValidationResult> validateResultsAgainstMarkdown(File markdownFile,
                                                                        File dataDirectory,
                                                                        File output) throws IOException {

        ParseMdService parseMdService = new ParseMdService(markdownFile);
        EndpointCallProvider endpointCallProvider = new GlobEndpointCallProvider(dataDirectory, "response.*.json");

        APIValidator validator = new APIValidator(endpointCallProvider, parseMdService);

        List<ValidationResult> results = validator.validate();

        HtmlOutputWriter writer = new HtmlOutputWriter(results);
        writer.outputReport(output);

        return results;
    }

}
