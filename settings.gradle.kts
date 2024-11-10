pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "web-app"

include(":core:data")
include(":core:entities")
include(":core:server")
include(":exposed-h2")
include(":kodein-ktor")
