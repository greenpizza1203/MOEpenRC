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
        versionCode = 39
        versionName = "OpenRC 6.1"
    }

    buildTypes["debug"].apply {
        isDebuggable = true
        isJniDebuggable = true
        isRenderscriptDebuggable = true

        ndk {
            abiFilters.add("armeabi-v7a")
        }
    }
//    ndkVersion = ("21.3.6528147")
    compileOptions {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }

}

dependencies {
    implementation(fileTree("../libs/"))
    implementation(kotlin("stdlib-jdk8", "1.4.20"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
    implementation("org.openftc:easyopencv:1.4.1")

    implementation("com.acmerobotics.roadrunner:core:0.5.2")
    compileOnly("org.apache.commons:commons-math3:3.6.1")
    compileOnly("org.processing:core:3.3.7")

//    implementation("com.intel.realsense:librealsense:2.34.0-ftc265@aar")

}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/openftc/maven")
    maven(url = "https://maven.0x778.tk")
}
