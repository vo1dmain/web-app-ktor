plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "ru.vo1d.web"
version = "0.8.0"

kotlin {
    jvmToolchain(rootProject.extra["jvmVersion"] as Int)
}

dependencies {
    api(libs.kotlinx.datetime)

    implementation(libs.kotlinx.serialization.core)

    testImplementation(kotlin("test-junit"))
}