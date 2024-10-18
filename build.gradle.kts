plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.versions)
}

group = "ru.vo1d.web"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")

    kotlin {
        jvmToolchain(libs.versions.jvm.get().toInt())
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:server"))
    implementation(project(":exposed-h2"))
}