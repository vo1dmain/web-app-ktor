plugins {
    id("web.kotlin-jvm")
}

dependencies {
    api(libs.kotlinx.datetime)

    testImplementation(libs.kotlinx.coroutines.core)
}
