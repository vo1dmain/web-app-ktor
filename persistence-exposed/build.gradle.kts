plugins {
    id("web.kotlin-jvm")
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)
}