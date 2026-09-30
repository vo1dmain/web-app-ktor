plugins {
    id("web.kotlin-serialization")
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlinx.coroutines.core)
    testRuntimeOnly(libs.h2database.h2)
}
