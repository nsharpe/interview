plugins {
    id("java")
}

group = "org.example.media.player"

tasks.build{
    dependsOn(":media-player-endpoint-webapp:build")
    dependsOn(":media-player-endpoint-webapp:bootJar")
    dependsOn(":media-player-endpoint-sdk:build")
    dependsOn(":media-player-endpoint-sdk:openApiGenerate")
    dependsOn(":media-player-typescript-sdk:publishSdkLocally")
}


tasks.register("cleanAll") {
    dependsOn(":media-player-endpoint-webapp:clean")
    dependsOn(":media-player-endpoint-sdk:clean")
    dependsOn(":media-player-typescript-sdk:clean")
}