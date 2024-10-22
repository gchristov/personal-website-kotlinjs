plugins {
    // Allows publishing build scans when common tasks run from the project root, eg jsTest
    id("com.gradle.develocity") version("3.18.1")
}

rootProject.name = "personal-website-kotlinjs"

// Add or remove projects here from the common build. Alternatively, each project can be opened in isolation.
includeBuild("gradle-plugins")
includeBuild("landing-page-web")
includeBuild("proxy-web")

develocity {
    buildScan {
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")
        publishing.onlyIf { true }
    }
}