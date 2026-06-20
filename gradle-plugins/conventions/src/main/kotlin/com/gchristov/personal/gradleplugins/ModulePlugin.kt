package com.gchristov.personal.gradleplugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class NodeModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            plugins.apply(libs.findPlugin("personal-website-base-node").get().get().pluginId)
            plugins.apply("org.jetbrains.kotlin.plugin.serialization")
            extensions.configure(KotlinMultiplatformExtension::class.java) {
                sourceSets.maybeCreate("commonTest").dependencies {
                    api(libs.findLibrary("kotlinx-coroutines-test").get())
                }
            }
        }
    }
}
