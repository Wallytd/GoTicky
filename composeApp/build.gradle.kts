import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.googleGmsGoogleServices)
    kotlin("native.cocoapods")
}

kotlin {
    // Ensure shared source sets (iosMain, appleMain, etc.) are wired so expect/actuals resolve.
    applyDefaultHierarchyTemplate()

    cocoapods {
        summary = "GoTicky shared Compose module"
        homepage = "https://goticky.app"
        ios.deploymentTarget = "15.0"
        version = "1.0.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
        // Native Firebase pods to match dev.gitlive bindings.
        pod("FirebaseAuth")
        pod("FirebaseFirestore")
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            // Explicit bundle ID to satisfy linker (avoid "Cannot infer bundle ID" warning)
            binaryOption("bundleId", "org.example.project.ComposeApp")
        }
    }

    jvm()

    js {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting
        val androidMain by getting
        val jvmMain by getting
        val jsMain by getting {
            dependencies {
                implementation(npm("qrcode", "1.5.3"))
                implementation(devNpm("copy-webpack-plugin", "11.0.0"))
                // Firebase Web SDK for JS target so dev.gitlive Firebase can bind to the default app
                implementation(npm("firebase", "10.12.5"))
            }
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("com.google.android.gms:play-services-maps:19.2.0")
            implementation("com.google.maps.android:maps-compose:7.0.0")
            implementation("com.google.android.gms:play-services-location:21.3.0")
            implementation("com.google.zxing:core:3.5.4")
            implementation("com.google.firebase:firebase-auth-ktx:23.2.1")
            implementation("androidx.biometric:biometric:1.1.0")
            // CardView for IntroActivity neumorphic logo container
            implementation("androidx.cardview:cardview:1.0.0")
            // Remote image loading for profile previews (Sign-In avatar)
            implementation("io.coil-kt:coil-compose:2.7.0")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
            // Firebase Kotlin SDK (bumped to avoid iOS Firestore settings crash in 1.13.0).
            implementation("dev.gitlive:firebase-auth:2.4.0")
            implementation("dev.gitlive:firebase-firestore:2.4.0")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("com.google.zxing:core:3.5.4")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// Align toolchains to Java 17 to match dependencies built for 17.
kotlin.jvmToolchain(17)

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        // Google Maps key is supplied via Gradle/local.properties to avoid hardcoding secrets.
        val mapsKeyProvider = providers.gradleProperty("MAPS_API_KEY")
            .orElse(providers.environmentVariable("MAPS_API_KEY"))
        val mapsKeyFromGradle = mapsKeyProvider.getOrElse("")
        val mapsKeyFromLocal = runCatching {
            val props = Properties()
            val localPropsFile = rootProject.file("local.properties")
            if (localPropsFile.exists()) {
                localPropsFile.inputStream().use(props::load)
            }
            props.getProperty("MAPS_API_KEY").orEmpty()
        }.getOrElse { "" }
        manifestPlaceholders["MAPS_API_KEY"] = mapsKeyFromGradle.ifBlank { mapsKeyFromLocal }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.inappmessaging.display)
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.example.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}
