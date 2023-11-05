pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.2"
////                            # available:"0.60.3"
}

rootProject.name = "chesskt"
include(":shared")
include(":desktop")
