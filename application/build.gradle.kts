val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.24"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
}

group = "ro.marc.ptbox"
version = "0.0.1"

repositories {
    mavenCentral()
}

val koin_version = "3.5.0"

dependencies {
    implementation(project(":shared"))
    implementation(project(":adapters:db"))
    implementation(project(":adapters:amass"))
    implementation(project(":adapters:theharvester"))

    implementation(platform("io.insert-koin:koin-bom:$koin_version"))
    implementation("io.insert-koin:koin-core")
    implementation("io.insert-koin:koin-core-coroutines")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
}
