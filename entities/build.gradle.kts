val kotlinVersion: String by project
val serializationVersion = "1.3.3"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "ru.vo1d.web.entities"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}