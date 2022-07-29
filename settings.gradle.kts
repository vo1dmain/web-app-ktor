rootProject.name = "web-app-2"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.10"
        kotlin("plugin.serialization") version "1.7.10"
    }
}

include("app", "entities", "data", "orm")
