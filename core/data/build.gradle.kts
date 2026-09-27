plugins {
    id("web.kotlin-jvm")
}

dependencies {
    api(project(":core:entities"))

    implementation(libs.kodein.ktor)
    implementation(libs.kotlinx.coroutines.core)
}