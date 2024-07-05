plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id(libs.plugins.kotlin.plugin.serialization.get().pluginId) version
            libs.plugins.kotlin.plugin.serialization.get().version.toString()
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.google.services.get().pluginId)
    id(libs.plugins.firebase.crashlytics.get().pluginId)
    id(libs.plugins.devtools.ksp.get().pluginId) version
            libs.plugins.devtools.ksp.get().version.toString()
    alias(libs.plugins.openapi.generator)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.mytestapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mytestapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    sourceSets {
        getByName("main") {
            java.srcDir("${layout.buildDirectory.get()}/generated/open-api")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.named("preBuild") {
    dependsOn("openApiGenerate")
}

openApiGenerate {
    generatorName = "kotlin"
    inputSpec.set("$rootDir/example-api.json")
    outputDir.set("${layout.buildDirectory.get()}/generated/open-api")
    configOptions.set(
        mapOf(
            "moshiCodeGen" to "true",
            "dateLibrary" to "java8"
        )
    )
    additionalProperties.set(
        mapOf(
            "useCoroutines" to "true",
            "library" to "jvm-retrofit2",
            "sourceFolder" to "src/main/java"
        )
    )
    id = "ExampleApp"
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.appcompat.resources)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.constraint.layout)
    implementation(libs.play.integrity)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.kt.compose)
    implementation(libs.timber)
    implementation(libs.koin.android)
    implementation(platform(libs.retrofit2.retrofit.bom))
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.retrofit2.converter.scalars)
    implementation(platform(libs.okhttp3.okhttp.bom))
    implementation(libs.okhttp3.okhttp)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.squareup.moshi)
    implementation(libs.squareup.moshi.kotlin)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    //    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
//    ksp(libs.squareup.moshi.kotlin.codegen)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.koin.test.junit4)
    androidTestImplementation(libs.koin.android.test)
    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
}