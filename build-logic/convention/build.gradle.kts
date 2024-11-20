import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}


// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.gradle)
    compileOnly(libs.common)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.firebase.crashlytics.gradle)
    compileOnly(libs.perf.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.symbol.processing.gradle.plugin)
    compileOnly(libs.androidx.room.compiler.v251)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}