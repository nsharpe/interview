plugins {
    id("boot-library")
}

tasks.build{
    dependsOn(":MediaManagementWebApp:build")
    dependsOn(":MediaManagementSdk:build")
//    dependsOn("org.example.media.player.sdk:media-player-sdk")
}

group = "org.example.media.management"