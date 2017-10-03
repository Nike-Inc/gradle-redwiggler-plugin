package com.nike.redwiggler.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class EndpointSpecificationTask extends DefaultTask {
    def endpointSpecificationProvider

    @TaskAction
    void noop() {
    }
}
