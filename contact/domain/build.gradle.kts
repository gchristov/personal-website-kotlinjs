import com.gchristov.personal.gradleplugins.envSecret

val packageId = "com.gchristov.personal.contact.domain"

plugins {
    alias(libs.plugins.personal.website.node.module)
    alias(libs.plugins.personal.website.build.config)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.common.kotlin)
                implementation(libs.common.slack)
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
            name = "CONTACT_SLACK_WEBHOOK_URL",
            value = project.envSecret("CONTACT_SLACK_WEBHOOK_URL")
        )
    }
}
