pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "binary-compatibility-validator" -> useModule("org.jetbrains.kotlinx:binary-compatibility-validator:${requested.version}")
            }
        }
    }

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
}

rootProject.name = "krow"

include(":krow-core")
include(":docs")
