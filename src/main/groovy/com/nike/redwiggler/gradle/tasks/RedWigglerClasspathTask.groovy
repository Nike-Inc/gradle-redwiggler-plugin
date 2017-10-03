package com.nike.redwiggler.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class RedWigglerClasspathTask extends DefaultTask {

    ClassLoader classLoader;

    @TaskAction
    public void resolveClasspath() {
        def resolvedConfiguration = project.configurations.redwiggler.resolvedConfiguration
        def files = resolvedConfiguration.files
        def classpath = files.collect{it.toURI().toURL()}
        classLoader = new URLClassLoader(classpath as URL[])
    }

}
