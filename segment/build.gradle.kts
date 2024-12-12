plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}

android {
    namespace = "org.edx.mobile.segment"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "org.edx"
            artifactId = "segment-analytics"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }

            // Include metadata if needed
            pom {
                name = "Segment Analytics Plugin"
                description =
                    "This plugin aims to provide Segment analytics support for OpenEdX Application"
                url = "https://github.com/farhan-arshad-dev/edx-mobile-android-segment-analytics"
            }
        }
    }
}

dependencies {
    implementation(libs.foundation)

    implementation(libs.segment.analytics.kotlin)
    implementation(libs.segment.destination.firebase)
    implementation(libs.braze.segment.kotlin)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
