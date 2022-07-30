rootProject.name = "web-app-2"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.10" apply false
        kotlin("plugin.serialization") version "1.7.10" apply false
    }
}

include("app", "entities", "data", "orm")
