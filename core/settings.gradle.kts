rootProject.name = "core"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.10" apply false
        kotlin("plugin.serialization") version "1.7.10" apply false
    }
}

include("server", "data", "entities")