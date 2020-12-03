import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    id("com.android.application")
    kotlin("android")
//    id("org.moeftc.hotcode") version "1.9"
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
            abiFilters.add("armeabi-v7a")
        }
    }
    ndkVersion = ("21.3.6528147")
    compileOptions {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }

}

dependencies {
    implementation(files("../libs/FtcRobotController-extremeTurbo-debug.aar"))
    implementation(kotlin("stdlib-jdk8","1.4.20"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.4.1")
    implementation("org.openftc:easyopencv:1.4.1")

    implementation("com.acmerobotics.roadrunner:core:0.5.2")
    implementation("org.apache.commons:commons-math3:3.6.1")
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/openftc/maven")
}
