package com.gchristov.personal.gradleplugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class BrowserBinaryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            plugins.apply("base-browser-plugin")
            extensions.configure(KotlinMultiplatformExtension::class.java) {
                js(IR) {
                    binaries.executable()
                }
            }
            // Copy the output binaries to their final destination
            tasks.named("assemble") {
                doLast {
                    copy {
                        from(file(layout.projectDirectory.file("Dockerfile")))
                        into(binaryRootDirectory())
                    }
                }
            }
        }
    }
}