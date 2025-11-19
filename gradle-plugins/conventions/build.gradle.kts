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
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
}