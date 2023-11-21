import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id ("io.gitlab.arturbosch.detekt")
}

val apikeyPropertiesFile = rootProject.file("apikey.properties")
val apikeyProperties = Properties().apply {
    load(FileInputStream(apikeyPropertiesFile))
}

android {
    namespace = "com.example.appmarvels"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.appmarvels"
        minSdk = 25
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField ("String", "PUBLIC_KEY", apikeyProperties.getProperty("PUBLIC_KEY"))
        buildConfigField ("String", "PRIVATE_KEY", apikeyProperties.getProperty("PRIVATE_KEY"))
        buildConfigField ("String", "BASE_URL", "\"https://gateway.marvel.com:443/v1/public/\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    // AndroidX
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    val coroutines_version = "1.6.2"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")

    // Material design
    implementation("com.google.android.material:material:1.9.0")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")

    // OkHttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson
    implementation ("com.google.code.gson:gson:2.8.6")

    //Javax Inject
    implementation ("javax.inject:javax.inject:1")

    // ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.2")

    // Navigation
    val nav_version = "2.5.3"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Paging3
    val paging_version = "3.1.1"
    implementation ("androidx.paging:paging-runtime-ktx:$paging_version")

    // Dagger Hilt
    val hilt_version = "2.42"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    // Room
    val room_version = "2.4.2"
    implementation ("androidx.room:room-ktx:$room_version")
    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    kapt ("com.github.bumptech.glide:compiler:4.13.2")

    // Other Libs
    implementation ("com.facebook.shimmer:shimmer:0.5.0")

    testImplementation ("junit:junit:4.13.2")

    implementation ("junit:junit:4.13.2")
    implementation ("androidx.arch.core:core-testing:2.1.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    implementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    // Instrumentation tests
    val espresso_version = "3.4.0"
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")

    androidTestImplementation ("androidx.test:runner:1.4.0")
    androidTestUtil ("androidx.test:orchestrator:1.4.1")

    androidTestImplementation ("androidx.test.espresso:espresso-core:$espresso_version")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:$espresso_version")

    val fragment_version = "1.6.0-alpha03"
    implementation ("androidx.fragment:fragment-ktx:1.4.1")
    debugImplementation ("androidx.fragment:fragment-testing:1.4.1")

    implementation ("androidx.test.espresso:espresso-idling-resource:3.5.1")


    // For instrumented tests
    androidTestImplementation ("com.google.dagger:hilt-android-testing:$hilt_version")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:$hilt_version")

    androidTestImplementation ("com.squareup.okhttp3:mockwebserver:4.9.3")

    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

project.afterEvaluate {
    tasks.named("preBuild") {
        dependsOn("detekt")
    }
}

detekt {
    toolVersion = "1.23.3"
    val inputDirFiles =
    rootProject.subprojects.map { module ->
        "$module.projectDir/src/main/java"
    }
    source.setFrom(files(inputDirFiles))
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
    autoCorrect = true

    tasks.named("detekt").configure {
        reports {
            xml.required.set(true)
            html.required.set(true)
            txt.required.set(true)
        }
    }
}