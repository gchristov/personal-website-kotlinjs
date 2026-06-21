import com.gchristov.personal.gradleplugins.envSecret

val packageId = "com.gchristov.personal.common.monitoring"

plugins {
    alias(libs.plugins.personal.website.node.module)
    alias(libs.plugins.personal.website.build.config)
}

group = "com.gchristov.personal.common"
version = "0.0.1"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.kotlin)
                implementation(projects.slack)
            }
        }
    }
}

buildkonfig {
    packageName = packageId
    exposeObjectWithName = "BuildConfig"
    defaultConfigs {
        buildConfigField(
            type = com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            name = "MONITORING_SLACK_URL",
            value = project.envSecret("MONITORING_SLACK_URL")
        )
    }
}
