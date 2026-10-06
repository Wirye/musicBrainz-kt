@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = "com.github.wirye"
version = "1.0.0"

kotlin {
    jvm()

    androidLibrary {
        namespace = "com.github.wirye.musicBrainzkt"
        compileSdk = 35
        minSdk = 24

        withJava()
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    sourceSets {
        jvmTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.cio)
            implementation(libs.kotlinx.coroutines.test)
        }

        commonMain.dependencies {
            api(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

//  signAllPublications()

    coordinates(group.toString(), "musicBrainz-kt", version.toString())

    pom {
        name = "musicBrainz-kt"
        description = "Kotlin Multiplatform wrapper for MusicBrainz API"
        inceptionYear = "2026"
        url = "https://github.com/Wirye/musicBrainz-kt"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "Wirye"
                name = "Wirye"
                url = "https://github.com/Wirye"
            }
        }
        scm {
            url = "https://github.com/Wirye/musicBrainz-kt"
            connection = "scm:git:git://github.com/Wirye/musicBrainz-kt.git"
            developerConnection = "scm:git:ssh://github.com/Wirye/musicBrainz-kt.git"
        }
    }
}