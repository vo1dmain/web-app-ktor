plugins {
    id("web.kotlin-serialization")
}

dependencies {
    implementation(project(":domain"))

    api(libs.ktor.server.core)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.compression)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.html.builder)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.status.pages)

    implementation(libs.ktor.serialization.kotlinx.json)

    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.ktor.client.logging)
    testImplementation(libs.ktor.server.test.host)
    testRuntimeOnly(libs.logback.classic)
}