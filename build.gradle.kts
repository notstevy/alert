plugins {
    id("java-library")
    id("java")
    id("maven-publish")
    id("signing")
    kotlin("jvm") version "2.0.0"
}

group = "me.obsilabor"
version = "1.1.0"

repositories {
    mavenCentral()
}

signing {
    sign(publishing.publications)
}

tasks {
    compileJava {
        options.release.set(11)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}

val jarThing by tasks.registering(Jar::class) {
    archiveClassifier.set("jar")
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    kotlin.runCatching {
        repositories {
            maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
                name = "ossrh"
                credentials(PasswordCredentials::class) {
                    username = (property("ossrhUsername") ?: return@credentials) as String
                    password = (property("ossrhPassword") ?: return@credentials) as String
                }
            }
        }
    }.onFailure {
        println("Unable to add publishing repositories: ${it.message}")
    }

    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(jarThing.get())

            this.groupId = project.group.toString()
            this.artifactId = project.name.toLowerCase()
            this.version = project.version.toString()

            pom {
                name.set(project.name)
                description.set("Alert is a really simple and blazing fast event listening utility.")

                developers {
                    developer {
                        name.set("mooziii")
                    }
                }

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/mooziii/alert/blob/main/LICENSE")
                    }
                }

                url.set("https://github.com/mooziii/alert")

                scm {
                    connection.set("scm:git:git://github.com/mooziii/alert.git")
                    url.set("https://github.com/mooziii/alert/tree/main")
                }
            }
        }
    }
}

