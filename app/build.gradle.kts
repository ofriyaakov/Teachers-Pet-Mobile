import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}



android {
    namespace = "com.example.teacherspet"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.teacherspet"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        buildConfigField("String", "CLOUD_NAME", "\"${project.properties["CLOUD_NAME"] ?: ""}\"")
//        buildConfigField("String", "API_KEY", "\"${project.properties["API_KEY"] ?: ""}\"")
//        buildConfigField("String", "API_SECRET", "\"${project.properties["API_SECRET"] ?: ""}\"")
//        buildConfigField("String", "TMDB_BASE_URL", "\"${project.properties["TMDB_BASE_URL"] ?: ""}\"")
//        buildConfigField("String", "TMDB_POSTER_BASE_URL", "\"${project.properties["TMDB_POSTER_BASE_URL"] ?: ""}\"")
//        buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"${project.properties["TMDB_ACCESS_TOKEN"] ?: ""}\"")

        //load the values from .properties file
        val keystoreFile = project.rootProject.file("keystore.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        //return empty key in case something goes wrong
        val apiKey = properties.getProperty("GEMINI_API_KEY") ?: ""

        buildConfigField(
            type = "String",
            name = "GEMINI_API_KEY",
            value = apiKey
        )


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    dataBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.cloudinary.android)
    implementation(libs.generativeai)
//    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
//    implementation("com.google.firebase:firebase-analytics")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth:22.1.0")
    implementation("com.google.android.gms:play-services-auth:20.6.0")
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    implementation("com.google.firebase:firebase-storage:20.2.1")

}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
}
