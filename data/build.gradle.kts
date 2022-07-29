plugins {
    kotlin("jvm")
}

group = "ru.vo1d.web"
version = "0.7.0"

repositories {
    mavenCentral()
}

val coroutinesVersion: String by project

dependencies {
    api(project(":entities"))

    implementation(kotlin("reflect", kotlin.coreLibrariesVersion))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}