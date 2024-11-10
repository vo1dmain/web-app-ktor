plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "ru.vo1d.web"
version = "0.9.0"

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}

dependencies {
    implementation(project(":core:data"))

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

    api(project(":kodein-ktor"))

    testImplementation(kotlin("test"))

    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.ktor.client.logging)
    testImplementation(libs.ktor.server.test.host)
}