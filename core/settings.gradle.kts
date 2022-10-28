rootProject.name = "core"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.20" apply false
        kotlin("plugin.serialization") version "1.7.20" apply false
    }
}

include("server", "data", "entities")