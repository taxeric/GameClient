plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.lanier.gameclient"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.lanier.gameclient"
        minSdk = 31
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    dataBinding {
        enable = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    val okhttpBomVersion = "4.12.0"
    implementation(platform("com.squareup.okhttp3:okhttp-bom:$okhttpBomVersion"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

// https://mvnrepository.com/artifact/com.github.florent37/ViewAnimator
    implementation("com.github.florent37:ViewAnimator:1.1.2")

    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper:3.0.14")

    //datastore
    val datastoreVersion = "1.0.0"
    implementation("androidx.datastore:datastore-preferences:$datastoreVersion")

    //coroutine
    val coroutineVersion = "1.7.1"
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")

    //vm & lifecycle
    val lifecycleVersion = "2.6.2"
    val actKtxVersion = "1.7.2"
    val fraKtxVersion = "1.6.2"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-ktx:$actKtxVersion")
    implementation("androidx.fragment:fragment-ktx:$fraKtxVersion")

    val jacksonVersion = "2.16.1"
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    //coil
    val coilVersion = "2.4.0"
    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-gif:$coilVersion")
}