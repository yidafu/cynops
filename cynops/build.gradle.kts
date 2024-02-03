plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
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
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
                implementation(libs.kotlinx.datetime)
//                implementation("io.kotest:kotest-extensions-mockserver:4.4.3")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.mockk)
                implementation(libs.slf4j.api)
                implementation(libs.kotest.runner.junit5)

                // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
                implementation(libs.slf4j.simple)
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
