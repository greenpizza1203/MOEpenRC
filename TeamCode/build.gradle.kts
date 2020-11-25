import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.moeftc.hotcode") version "1.9"
}

android {

    compileSdkVersion(30)


    aaptOptions {
        noCompress("tflite")
    }

    defaultConfig {
        applicationId = "com.qualcomm.ftcrobotcontroller"
        minSdkVersion(25)
        targetSdkVersion(30)
        versionCode = 38
        versionName = "OpenRC 6.0"
    }

    buildTypes["debug"].apply {
        isDebuggable = true
        isJniDebuggable = true
        isRenderscriptDebuggable = true

        ndk {

            abiFilter("armeabi-v7a")
        }

    }

    compileOptions {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }

}

dependencies {
    implementation(files("../libs/FtcRobotController-extremeTurbo-debug.aar"))
    implementation(embeddedKotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("org.openftc:easyopencv:1.3.2")
    implementation("com.acmerobotics.dashboard:dashboard:0.3.10") {
        exclude("org.firstinspires.ftc")
    }
    implementation("com.acmerobotics.roadrunner:core:0.5.1")
    implementation("org.apache.commons:commons-math3:3.6.1")
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/openftc/maven")
}
