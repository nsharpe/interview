plugins {
    id("boot-library")
}

tasks.build{
    dependsOn(":media-comment:webapp:build")
    dependsOn(":media-comment:webapp:bootJar")
    dependsOn(":media-comment:sdk:build")
    dependsOn(":media-comment:sdk:openApiGenerate")
    dependsOn(":media-comment:typescript-sdk:publishSdkLocally")
}

group = "org.amoeba.example.media.management"