plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins.register("base-browser-plugin") {
        id = "base-browser-plugin"
        implementationClass = "com.gchristov.personal.gradleplugins.BaseBrowserPlugin"
    }
    plugins.register("browser-binary-plugin") {
        id = "browser-binary-plugin"
        implementationClass = "com.gchristov.personal.gradleplugins.BrowserBinaryPlugin"
    }
    plugins.register("base-node-plugin") {
        id = "base-node-plugin"
        implementationClass = "com.gchristov.personal.gradleplugins.BaseNodePlugin"
    }
    plugins.register("node-module-plugin") {
        id = "node-module-plugin"
        implementationClass = "com.gchristov.personal.gradleplugins.NodeModulePlugin"
    }
    plugins.register("build-config-plugin") {
        id = "build-config-plugin"
        implementationClass = "com.gchristov.personal.gradleplugins.BuildConfigPlugin"
    }
    plugins.register("node-binary-plugin") {
        id = "node-binary-plugin"
        implementationClass = "com.gchristov.personal.gradleplugins.NodeBinaryPlugin"
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    // Allows these to be available and applied in the pre-compiled conventions plugin
    implementation(libs.kotlin.serialization.gradlePlugin)
    implementation(libs.buildKonfig.gradlePlugin)
}
