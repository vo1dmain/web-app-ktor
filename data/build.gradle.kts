plugins {
    kotlin("jvm")
}

group = "ru.vo1d.web"
version = "0.8.0"

repositories {
    mavenCentral()
}

val coroutinesVersion: String by project

dependencies {
    api(project(":entities"))

    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}