@file:Suppress("ClassName", "unused")

object Versions {
    // android
    const val compileSdk = 27
    const val minSdk = 14
    const val minSdkConductor = 16
    const val targetSdk = 27
    const val versionCode = 1
    const val versionName = "1.0"

    const val androidGradlePlugin = "3.1.2"
    const val archLifecycle = "1.1.1"
    const val conductor = "2.1.4"
    const val kotlin = "1.2.41"
    const val mavenGradle = "2.1"
    const val rxJava = "2.1.14"
    const val support = "27.1.1"
}

object Deps {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    const val archLifecycleCompiler = "android.arch.lifecycle:compiler:${Versions.archLifecycle}"
    const val archLifecycleRuntime = "android.arch.lifecycle:runtime:${Versions.archLifecycle}"

    const val conductor = "com.bluelinelabs:conductor:${Versions.conductor}"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin}"

    const val mavenGradlePlugin = "com.github.dcendents:android-maven-gradle-plugin:${Versions.mavenGradle}"

    const val supportAnnotations = "com.android.support:support-annotations:26.1.0" // no bug
    const val supportAppCompat = "com.android.support:appcompat-v7:${Versions.support}"

    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
}