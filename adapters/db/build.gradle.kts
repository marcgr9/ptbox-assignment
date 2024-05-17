plugins {
    kotlin("jvm") version "1.9.24"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
}

group = "ro.marc.pastebin"
version = "0.0.1"

repositories {
    mavenCentral()
}

val exposedVersion: String = "0.44.0"

dependencies {
    implementation(project(":shared"))

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
    implementation("com.h2database:h2:2.1.214")

    implementation("com.zaxxer:HikariCP:4.0.3")

    implementation("io.insert-koin:koin-core:3.5.0")
    implementation("io.insert-koin:koin-core-coroutines:3.5.0")

    implementation("org.postgresql:postgresql:42.2.2")
}
