val kotlinVersion: String by project

plugins {
    kotlin("jvm")
}

group = "ru.vo1d.web"
version = "0.4.0"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":entities"))

    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
}