val serializationVersion: String by project
val exposedVersion: String by project
val h2Version: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "ru.vo1d.web"
version = "0.5.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":data"))

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")

    implementation("com.h2database:h2:$h2Version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    testImplementation(kotlin("test-junit"))
}