plugins {
    id("java")
}

group = "org.example.media.player"

tasks.build{
    dependsOn(":media-player-endpoint-webapp:build")
    dependsOn(":media-player-endpoint-webapp:bootJar")
    dependsOn(":media-player-endpoint-sdk:build")
    dependsOn(":media-player-endpoint-sdk:openApiGenerate")
    //dependsOn(":admin-typescript-sdk:publishSdkLocally")
}