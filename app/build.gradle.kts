plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // hilt関連設定,
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.learningcleanarchitecture"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.learningcleanarchitecture"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // https://qiita.com/kazuma_f/items/8c15e7087623e8f6706b#%E3%82%B9%E3%82%AD%E3%83%BC%E3%83%9E%E3%81%AE%E6%9B%B8%E3%81%8D%E5%87%BA%E3%81%97%E3%83%95%E3%82%A9%E3%83%AB%E3%83%80%E3%81%AE%E6%8C%87%E5%AE%9A
        ksp {
            arg("room.schemaLocation","$projectDir/schemas")
        }
    }

    // flavorで分けるための設定.
    flavorDimensions += "app_type"
    productFlavors {
        create("develop") {
            dimension = "app_type"
        }
        create("product") {
            dimension = "app_type"
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // この書き方をする場合はsetting.gradle.ktsを参照.
    implementation(projects.core.database)
    implementation(projects.core.data)
    implementation(projects.core.domain)

    // hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)

    // livedata
    implementation(libs.androidx.runtime.livedata)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // zxing(QR 生成)
    implementation(libs.zxing.android.embedded)
    // QR 読み取り
    implementation(libs.play.services.code.scanner)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}