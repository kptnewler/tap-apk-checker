buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://nexus.xmxdev.com/repository/maven-public/") }
        mavenLocal()
    }
    dependencies {
        classpath("com.taptap.apkchecker:apk-checker-plugin:1.0.0-SNAPSHOT")

        // agp
        classpath("com.android.tools.build:gradle:4.2.2")
        // kotlin gradle plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
        // publish
        classpath("info.hellovass:h-publish-plugin:1.0.37-SNAPSHOT")
//        classpath("com.vanniktech:gradle-dependency-graph-generator-plugin:0.8.0")
    }
}

group = "com.taptap.apkchecker"
version = "1.0.0-SNAPSHOT"

subprojects {

    group = rootProject.group
    version = rootProject.version

    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://nexus.xmxdev.com/repository/maven-public/") }
        mavenLocal()
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}
