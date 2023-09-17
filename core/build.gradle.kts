@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.catalog.update)
}

group = "ru.vo1d.web"
version = "1.0.0"

ext {
    set("jvmVersion", 11)
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    api(project(":server"))
    api(project(":data"))
}

subprojects {
    rootProject.tasks.clean {
        dependsOn(tasks.findByName("clean"))
    }
    rootProject.tasks.compileKotlin {
        dependsOn(tasks.findByName("test"))
    }
}

