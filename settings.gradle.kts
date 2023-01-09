pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "web-app"

includeBuild("core")
include(":exposed-h2")

