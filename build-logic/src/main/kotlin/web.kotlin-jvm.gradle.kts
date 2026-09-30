plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "ru.vo1d.web"

val catalog = the<VersionCatalogsExtension>().named("libs")

kotlin {
    jvmToolchain(catalog.findVersion("jvm").get().requiredVersion.toInt())
}

dependencies {
    testImplementation(kotlin("test"))
}
