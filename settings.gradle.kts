rootProject.name = "web-app"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.10" apply false
        kotlin("plugin.serialization") version "1.7.10" apply false
    }
}
includeBuild("core")
include("exposed-h2")

