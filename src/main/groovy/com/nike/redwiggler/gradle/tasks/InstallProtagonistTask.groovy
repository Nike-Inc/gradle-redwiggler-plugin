package com.nike.redwiggler.gradle.tasks

import com.nike.redwiggler.gradle.RedWigglerPluginExtension
import org.gradle.api.tasks.Exec

class InstallProtagonistTask extends Exec {

    InstallProtagonistTask() {
        RedWigglerPluginExtension ext = project.extensions.redwiggler
        println("Using " + ext.blueprintConfiguration.prefix + " as node prefix")
        ext.blueprintConfiguration.prefix.mkdirs()
        commandLine = ['npm', 'install', 'protagonist@1.6.8', "--global", "--prefix", ext.blueprintConfiguration.prefix.getAbsolutePath()]
    }
}
