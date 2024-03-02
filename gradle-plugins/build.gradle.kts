plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins.register("base-browser-plugin") {
        id = "base-browser-plugin"
        implementationClass = "com.gchristov.thecodinglove.gradleplugins.BaseBrowserPlugin"
    }
    plugins.register("browser-binary-plugin") {
        id = "browser-binary-plugin"
        implementationClass = "com.gchristov.thecodinglove.gradleplugins.BrowserBinaryPlugin"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
}

val taskNames = listOf("clean", "assemble")
taskNames.forEach {  taskName ->
    tasks.register("${taskName}All") {
        tasks.findByName(taskName)?.let { dependsOn(it) }
        dependsOn(gradle.includedBuilds.map { it.task(":${taskName}All") })
        subprojects.map { it.tasks.findByName(taskName)?.let { dependsOn(it) } }
    }
}