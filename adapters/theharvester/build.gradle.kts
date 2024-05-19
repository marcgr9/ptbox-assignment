plugins {
    kotlin("jvm") version "1.9.24"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
}

group = "ro.marc.pastebin"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))

    implementation("com.github.docker-java:docker-java-core:3.3.6")
    implementation("com.github.docker-java:docker-java:3.3.6")
    implementation("com.github.docker-java:docker-java-transport-zerodep:3.3.6")

    implementation("io.insert-koin:koin-core:3.5.0")
    implementation("io.insert-koin:koin-core-coroutines:3.5.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
}
