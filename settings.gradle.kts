pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")

        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "cynops-logger"
include(":cynops-core")
