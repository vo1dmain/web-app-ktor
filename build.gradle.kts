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
        jvmToolchain(11)
    }
}

dependencies {
    implementation("ru.vo1d.web:core")
    implementation(project(":exposed-h2"))
}

subprojects {
    rootProject.tasks.clean {
        dependsOn(tasks.findByName("clean"))
    }
    rootProject.tasks.compileKotlin {
        dependsOn(tasks.findByName("test"))
    }
}