plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

allprojects {
    group = "ru.vo1d.web"
    apply(plugin = "org.jetbrains.kotlin.jvm")
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation("ru.vo1d.web:core")
    implementation(project("exposed-h2"))
}