import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
}

group = "ru.otus.otuskotlin.marketplace"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version
    repositories {
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}


