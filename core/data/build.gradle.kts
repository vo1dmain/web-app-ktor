plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "ru.vo1d.web"
version = "0.9.0"

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}

dependencies {
    api(project(":core:entities"))

    implementation(kotlin("reflect"))
    implementation(libs.kotlinx.coroutines.core)
}