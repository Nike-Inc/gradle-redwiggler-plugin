package com.nike.redwiggler.gradle.tasks

import com.nike.redwiggler.gradle.RedWigglerPluginExtension
import org.gradle.api.tasks.TaskAction

class BlueprintEndpointSpecificationProviderTask extends EndpointSpecificationTask {

    @TaskAction
    void findEndpointSpecificationProvider() {
        RedWigglerPluginExtension ext = project.redwiggler
        def classloader = project.tasks.redwigglerGenerateClasspath.classLoader
        def BlueprintEndpointSpecificationProvider = classloader.loadClass("com.nike.redwiggler.blueprint.BlueprintSpecificationProvider")

        def JavaConversions = classloader.loadClass("scala.collection.JavaConversions")
        def path = JavaConversions.asScalaBuffer([new File(ext.blueprintConfiguration.prefix.getAbsolutePath(), "lib/node_modules")]).toSeq()
        def ProtagonistBlueprintParser = classloader.loadClass("com.nike.redwiggler.blueprint.parser.ProtagonistBlueprintParser")
        def parser = ProtagonistBlueprintParser.apply(path)
        endpointSpecificationProvider = BlueprintEndpointSpecificationProvider.apply(ext.blueprintFile, parser)
    }
}
