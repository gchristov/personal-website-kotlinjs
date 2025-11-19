package com.gchristov.personal.gradleplugins

import org.gradle.api.Project
import org.gradle.api.file.Directory

fun Project.binaryRootDirectory(): Directory = layout.buildDirectory.dir("dist/js").get()