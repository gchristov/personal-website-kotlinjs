import com.gchristov.personal.gradleplugins.binaryRootDirectory

plugins {
    alias(libs.plugins.personal.website.browser.binary)
}

// Bundle resources specific to this binary
tasks.named("assemble") {
    doLast {
        copy {
            from(file(layout.projectDirectory.file("default.conf.template")))
            into(binaryRootDirectory())
        }
    }
}