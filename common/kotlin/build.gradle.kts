plugins {
    alias(libs.plugins.personal.website.base.node)
}

group = "com.gchristov.personal.common"
version = "0.0.1"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kodein)
                api(libs.touchlab.kermit)
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.serialization.json)
                api(libs.arrow.core)
            }
        }
    }
}
