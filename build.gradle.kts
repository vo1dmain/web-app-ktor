plugins {
    application
    id("web.kotlin-jvm")
    alias(libs.plugins.gradle.versions)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:server"))
    implementation(project(":exposed-h2"))
    implementation(libs.kodein.ktor)
}