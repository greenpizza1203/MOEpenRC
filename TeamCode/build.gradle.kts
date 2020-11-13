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
        versionCode = 37
        versionName = "OpenRC 5.5"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation(files("../libs/FtcRobotController-extremeTurbo-debug.aar"))
    implementation(embeddedKotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    compileOnly("org.openftc:easyopencv:1.3.2")
    compileOnly("org.processing", "core", "3.3.7")
//    compileOnly("com.acmerobotics.dashboard:dashboard:0.3.9")
    implementation("com.acmerobotics.roadrunner:core:0.5.1")
//    compileOnly("org.apache.commons:commons-math3:3.6.1")
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/openftc/maven")
}
