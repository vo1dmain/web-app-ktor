val serializationVersion: String by project
val exposedVersion: String by project
val h2Version: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "ru.vo1d.web.orm"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":data"))
    implementation(project(":entities"))

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.h2database:h2:$h2Version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
}