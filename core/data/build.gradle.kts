version = "0.8.0"

val coroutinesVersion: String by project

dependencies {
    api(project(":entities"))

    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}