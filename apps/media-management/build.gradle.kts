plugins {
    id("boot-library")
}

tasks.build{
    dependsOn(":media-management:media-management-webapp:build")
    dependsOn(":media-management:media-management-webapp:bootJar")
    dependsOn(":media-management:media-management-sdk:build")
    dependsOn(":media-management:media-management-sdk:openApiGenerate")
    dependsOn(":media-management:media-management-typescript-sdk:publishSdkLocally")
}

group = "org.amoeba.example.media.management"