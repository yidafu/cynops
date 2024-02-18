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
                implementation(libs.ktor.client.cio)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.koin.core)
//                implementation(libs.okio.core)
                implementation(libs.kotlinx.io.core)

                implementation(libs.ktor.client.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
                implementation(libs.kotlinx.datetime)
                implementation(libs.koin.test)
                implementation(libs.kotest.framework.engine)
//                implementation(libs.okio.fakefilesystem)

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

                implementation(libs.ktor.client.cio)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }
        val linuxMain by getting {
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)

//                implementation(libs.okio.nodefilesystem)
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    // DO NOT add bellow dependencies
//    add("kspAndroid", libs.koin.ksp.compiler)
//    add("kspIosX64", libs.koin.ksp.compiler)
//    add("kspIosArm64", libs.koin.ksp.compiler)
//    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
}

android {
    namespace = "dev.yidafu.cynops"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
