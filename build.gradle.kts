buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.com.android.tools.build.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
