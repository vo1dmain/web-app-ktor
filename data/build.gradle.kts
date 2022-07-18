val kotlinVersion: String by project

plugins {
    kotlin("jvm")
}

group = "ru.vo1d.web.data"
version = "1.2.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":entities"))

    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
}