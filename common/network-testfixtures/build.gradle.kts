plugins {
    alias(libs.plugins.personal.website.node.module)
}

group = "com.gchristov.personal.common"
version = "0.0.1"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.test)
                implementation(projects.network)
            }
        }
    }
}
