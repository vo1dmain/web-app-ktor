plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "ru.vo1d.web"
version = "0.9.0"

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}

dependencies {
    implementation(project(":core:data"))

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)

    implementation(libs.h2database.h2)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(kotlin("test-junit"))
}