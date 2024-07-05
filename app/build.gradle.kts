plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
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

    sourceSets {
        getByName("main") {
            java.srcDir("${layout.buildDirectory.get()}/generated/open-api")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(platform(libs.retrofit2.retrofit.bom))
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.retrofit2.converter.scalars)
    implementation(platform(libs.okhttp3.okhttp.bom))
    ksp(libs.squareup.moshi.kotlin.codegen)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}