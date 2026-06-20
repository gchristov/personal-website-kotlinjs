plugins {
    alias(libs.plugins.personal.website.base.node)
}

group = "com.gchristov.personal.common"
version = "0.0.1"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.touchlab.kermit)
                implementation(libs.kotlinx.coroutines.core)
                api(libs.kotlin.test)
            }
        }
    }
}
