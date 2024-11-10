plugins {
    kotlin("jvm")
}

group = "org.kodein.di"
version = "7.22.1"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.kodein.di)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.sessions)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}