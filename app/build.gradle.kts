plugins {
    application
    id("web.kotlin-jvm")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment = project.hasProperty("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":api-ktor"))
    implementation(project(":persistence-exposed"))
    implementation(libs.kodein.ktor)
}
