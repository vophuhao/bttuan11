plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.1" apply false

}

android {
    namespace = "com.example.firebase"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.firebase"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.google.android.gms:play-services-auth:20.4.1")
    /**firebase dependencies*/
    implementation ("com.google.firebase:firebase-auth:21.1.0")
    implementation ("com.google.firebase:firebase-database:20.1.0")
    implementation(platform("com.google.firebase:firebase-bom:31.2.3"))
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-firestore:24.4.5")
    implementation ("com.google.firebase:firebase-storage:20.1.0")
    implementation ("com.firebaseui:firebase-ui-database:8.0.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//Gson
    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
    implementation("com.github.TutorialsAndroid:GButton:v1.0.19")
    implementation ("com.google.code.gson:gson:2.10.1")
   /* implementation("com.github.Sauravbhatt:Google-Sign-In-Button:1.0")*/
    implementation("com.cloudinary:cloudinary-android:2.3.1")

}
apply(plugin = "com.google.gms.google-services")