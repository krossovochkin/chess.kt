plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("maven-publish")
}

group = "com.krossovochkin.chesskt"
version = "0.1"

kotlin {
    androidTarget()

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "chesskt"
            isStatic = true
        }
    }

    js(IR) {
        nodejs()
    }

    wasmJs {
        nodejs()
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.junit)
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.junit)
            }
        }
        val wasmJsMain by getting
        val wasmJsTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}

android {
    compileSdk = 35
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
    }
    kotlin {
        jvmToolchain(17)
    }
    namespace = "com.krossovochkin.chesskt"
}
