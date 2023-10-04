import java.io.FileInputStream
import java.util.Properties

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
  keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.dagger.hilt.android")
  id("kotlin-kapt")
}

android {
  namespace = "com.dpfht.android.democityweather"
  compileSdk = 33

  signingConfigs {
    create("release") {
      storeFile = file(keystoreProperties["storeFile"] as String)
      storePassword = keystoreProperties["storePassword"] as String
      keyAlias = keystoreProperties["keyAlias"] as String
      keyPassword = keystoreProperties["keyPassword"] as String
    }
  }

  defaultConfig {
    minSdk = 21
    targetSdk = 33

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true

      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

      signingConfig = signingConfigs["release"]

      manifestPlaceholders["appNameSuffix"] = ""
      resValue("string", "running_mode", "")
    }
    debug {
      isMinifyEnabled = false
      isShrinkResources = false

      applicationIdSuffix = ".debug"
      versionNameSuffix = "-debug"

      manifestPlaceholders["appNameSuffix"] = " (debug)"
      resValue("string", "running_mode", " (debug)")
    }
  }

  flavorDimensions.add("default")

  productFlavors {
    create("prod") {
      applicationId = "com.dpfht.android.democityweather"
      versionCode = 2
      versionName = "1.1"

      manifestPlaceholders["appName"] = "Demo City Weather"
      resValue("string", "app_name", "Demo City Weather")
    }
    create("dev") {
      applicationId = "com.dpfht.android.democityweather.dev"
      versionCode = 2
      versionName = "1.1"

      manifestPlaceholders["appName"] = "Demo City Weather (DEV)"
      resValue("string", "app_name", "Demo City Weather (DEV)")
    }
  }

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
}

dependencies {

  implementation(project(":domain"))
  implementation(project(":data"))
  implementation(project(":framework"))
  implementation(project(":features:feature_splash"))
  implementation(project(":features:feature_error_message"))
  implementation(project(":features:feature_city_weather"))

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.9.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

  implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
  implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

  implementation("com.google.dagger:hilt-android:2.44")
  kapt("com.google.dagger:hilt-compiler:2.44")
}
