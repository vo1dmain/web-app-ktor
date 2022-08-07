allprojects {
    group = "ru.vo1d.web"
    apply(plugin = "org.jetbrains.kotlin.jvm")
    repositories {
        mavenCentral()
    }
}

plugins {
    `java-library`
    kotlin("jvm")
}

version = "1.0.0"

dependencies {
    api(project("server"))
    api(project("data"))
}