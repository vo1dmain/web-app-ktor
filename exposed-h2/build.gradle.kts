@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "ru.vo1d.web"
version = "0.8.0"

dependencies {
    implementation("ru.vo1d.web:core")

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)

    implementation(libs.h2database.h2)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(kotlin("test-junit"))
}