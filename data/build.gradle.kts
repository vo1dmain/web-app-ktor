val kotlinVersion: String by project

plugins {
    kotlin("jvm")
}

group = "ru.vo1d.web.data"
version = "1.3.0"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":entities"))

    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
}