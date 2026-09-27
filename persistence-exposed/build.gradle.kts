plugins {
    id("web.kotlin-jvm")
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.kodein.ktor)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)

    implementation(libs.h2database.h2)

    implementation(libs.kotlinx.serialization.json)
}