plugins {
  id("com.android.library")
  kotlin("android")
}
android {
  namespace = "com.mubarak.navigator"
  compileSdk = 34
  defaultConfig {
    minSdk = 33
    targetSdk = 34
    buildConfigField("int", "MIN_SDK", "33")
    buildConfigField("int", "TARGET_SDK", "34")
  }
  buildFeatures { compose = true }
  composeOptions { kotlinCompilerExtensionVersion = "1.5.14" }
  packaging { resources.excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1") }
}
dependencies {
  implementation(platform("androidx.compose:compose-bom:2024.06.00"))
  implementation("androidx.activity:activity-compose:1.9.0")
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.material3:material3")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
  // Mirror Gallery's on-device LLM API
  implementation("com.google.mediapipe:tasks-genai:0.10.27")
}
