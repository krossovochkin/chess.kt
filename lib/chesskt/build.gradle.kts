import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.mavenPublish)
}

group = "com.krossovochkin.chesskt"
version = "0.1.1"

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
        jvmToolchain(21)
    }
    namespace = "com.krossovochkin.chesskt"
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(group.toString(), "chesskt", version.toString())

    pom {
        name.set("chesskt")
        description.set("Kotlin multiplatform chess backend")
        inceptionYear.set("2022")
        url.set("https://github.com/krossovochkin/chess.kt")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("krossovochkin")
                name.set("Vasya Drobushkov")
                url.set("https://github.com/krossvochkin/")
            }
        }
        scm {
            url.set("https://github.com/krossovochkin/chess.kt/")
            connection.set("scm:git:git://github.com/krossovochkin/chess.kt.git")
            developerConnection.set("scm:git:ssh://git@github.com/krossovochkin/chess.kt.git")
        }
    }
}
