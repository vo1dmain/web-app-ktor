@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "ru.vo1d.web"
version = "0.8.0"

kotlin {
    jvmToolchain(rootProject.ext["jvmVersion"] as Int)
}

dependencies {
    api(project(":entities"))

    implementation(kotlin("reflect"))
    implementation(libs.kotlinx.coroutines.core)
}