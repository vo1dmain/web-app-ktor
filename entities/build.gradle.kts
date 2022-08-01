plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "ru.vo1d.web"
version = "0.8.0"

repositories {
    mavenCentral()
}

val serializationVersion: String by project
val datetimeVersion: String by project
dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")

    testImplementation(kotlin("test-junit"))
}