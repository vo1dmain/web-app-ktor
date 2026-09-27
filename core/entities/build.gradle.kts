plugins {
    id("web.kotlin-serialization")
}

dependencies {
    api(libs.kotlinx.datetime)

    implementation(libs.kotlinx.serialization.core)
}