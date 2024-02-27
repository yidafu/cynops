import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    `maven-publish`
    signing
//    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"

//    kotlin("plugin.serialization")
}


repositories {
    maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    gradlePluginPortal()
    google()
    mavenCentral()
}

publishing {
    // Configure all publications
    publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(tasks.register("${name}JavadocJar", Jar::class) {
            archiveClassifier.set("javadoc")
            archiveAppendix.set(this@withType.name)
        })

        // Provide artifacts information required by Maven Central
        pom {
            name.set("Kotlin Multiplatform library template")
            description.set("Dummy library to test deployment to Maven Central")
            url.set("https://github.com/Kotlin/multiplatform-library-template")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("JetBrains")
                    name.set("JetBrains Team")
                    organization.set("JetBrains")
                    organizationUrl.set("https://www.jetbrains.com")
                }
            }
            scm {
                url.set("https://github.com/Kotlin/multiplatform-library-template")
            }
        }
    }
}

signing {
    if (project.hasProperty("signing.gnupg.keyName")) {
        useGpgCmd()
        sign(publishing.publications)
    }
}


//configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
//    version.set("0.50.0")
//    debug.set(true)
//    android.set(false)
//    outputToConsole.set(true)
//    outputColorName.set("RED")
//
//    disabledRules.set(setOf("final-newline", "no-wildcard-imports")) // not supported with ktlint 0.48+
////    filter {
////        exclude("**/generated/**")
////        include("**/kotlin/**")
////    }
//}