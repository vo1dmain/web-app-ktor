plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "ru.vo1d.web"

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}

dependencies {
    api(libs.kotlinx.datetime)

    implementation(libs.kotlinx.serialization.core)

    testImplementation(kotlin("test-junit"))
}