plugins {
    kotlin("plugin.serialization")
}

version = "0.8.0"

val serializationVersion: String by project
val datetimeVersion: String by project
dependencies {
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")

    testImplementation(kotlin("test-junit"))
}