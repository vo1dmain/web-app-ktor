@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "ru.vo1d.web"
version = "0.8.1"

kotlin {
    jvmToolchain(8)
}

dependencies {
    implementation(project(":data"))

    api(libs.ktor.server.core)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.compression)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.html.builder)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.status.pages)

    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.logback.classic)

    api(libs.kodein.ktor.server.jvm)

    testImplementation(kotlin("test-junit"))

    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.ktor.client.logging)
    testImplementation(libs.ktor.server.tests)
}