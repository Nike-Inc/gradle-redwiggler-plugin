package com.nike.redwiggler.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Copyright 2017-present, Nike, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 **/
class EndpointSpecificationTask extends DefaultTask {
    def endpointSpecificationProvider

    @TaskAction
    void noop() {
    }
}
