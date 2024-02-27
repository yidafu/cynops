plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.nexus.publish)
}