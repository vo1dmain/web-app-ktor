plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.catalog.update)
}

group = "ru.vo1d.web"
version = "1.0.0"

extra.apply {
    set("jvmVersion", 11)
}

kotlin {
    jvmToolchain(rootProject.extra["jvmVersion"] as Int)
}

dependencies {
    api(project(":server"))
    api(project(":data"))
}

subprojects {
    rootProject.tasks.clean {
        dependsOn(tasks.findByName("clean"))
    }
    rootProject.tasks.compileKotlin {
        dependsOn(tasks.findByName("test"))
    }
}

