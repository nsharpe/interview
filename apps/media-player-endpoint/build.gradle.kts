plugins {
    id("java")
}

group = "org.amoeba.example.media.player"

tasks.build{
    dependsOn(":media-player-endpoint:media-player-endpoint-webapp:build")
    dependsOn(":media-player-endpoint:media-player-endpoint-webapp:bootJar")
    dependsOn(":media-player-endpoint:sdk:build")
    dependsOn(":media-player-endpoint:sdk:openApiGenerate")
    dependsOn(":media-player-endpoint:media-player-typescript-sdk:publishSdkLocally")
}
