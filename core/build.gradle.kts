@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.catalog.update)
}

group = "ru.vo1d.web"
version = "1.0.0"

kotlin {
    jvmToolchain(8)
}

dependencies {
    api(project(":server"))
    api(project(":data"))
}