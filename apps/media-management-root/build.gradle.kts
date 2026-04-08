plugins {
    id("boot-library")
}

tasks.build{
    dependsOn(":media-management-root:media-management-webapp:build")
    dependsOn(":media-management-root:media-management-webapp:bootJar")
    dependsOn(":media-management-root:media-management-sdk:build")
    dependsOn(":media-management-root:media-management-sdk:openApiGenerate")
    dependsOn(":media-management-root:media-management-typescript-sdk:publishSdkLocally")
}

group = "org.amoeba.example.media.management"