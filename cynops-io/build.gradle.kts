@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    id("module.publication")
}

kotlin {
    targetHierarchy.default()
//    wasmJs {
//        moduleName = "loki_logger"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "loki_logger.js"
//            }
//        }
//        binaries.library()
//    }
    js {
        nodejs()
    }
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        val androidMain by getting {
            dependencies {
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.coroutines.core)
//                implementation(libs.okio.core)
                implementation(libs.kotlinx.io.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
                implementation(libs.kotest.framework.engine)
//                implementation(libs.okio.fakefilesystem)

//                implementation("io.kotest:kotest-extensions-mockserver:4.4.3")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.mockk)
                implementation(libs.kotest.runner.junit5)
            }
        }

        val iosMain by getting {
            dependencies {
            }
        }
        val linuxMain by getting {
            dependencies {
            }
        }
        val jsMain by getting {
            dependencies {

//                implementation(libs.okio.nodefilesystem)
            }
        }
    }
}

android {
    namespace = "dev.yidafu.cynops"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
