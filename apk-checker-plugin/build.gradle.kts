import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    id("info.hellovass.h-publish-plugin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.2")
    implementation(gradleApi())
    implementation(localGroovy())
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    // 协程
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    // 反射库
    implementation("org.jooq:joor-java-8:0.9.13")
    // sh
    implementation("com.jaredrummler:ktsh:1.0.0")
    // GLog
    implementation("com.taptap.glog:gradle-logger:1.0.18-SNAPSHOT")
    // gson
    implementation("com.google.code.gson:gson:2.9.0")
}

gradlePlugin {
    plugins {
        create("apk-checker-plugin") {
            id = name
            implementationClass = "com.taptap.apk_checker_plugin.ApkCheckerPlugin"
        }
    }
    isAutomatedPublishing = false
}