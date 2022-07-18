val kotlinVersion: String by project
val serializationVersion: String by project
val datetimeVersion: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "ru.vo1d.web.entities"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime-jvm:$datetimeVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")

    testImplementation(kotlin("test-junit"))
}