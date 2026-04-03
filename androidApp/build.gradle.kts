plugins {
	alias(libs.plugins.androidApplication)
}

dependencies {
	implementation(project(":composeApp"))
	//implementation(libs.compose.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.components.ui.tooling.preview)
}

android {
    namespace = "com.krossovochkin.chesskt.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.krossovochkin.chesskt.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
