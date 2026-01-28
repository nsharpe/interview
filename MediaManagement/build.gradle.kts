plugins {
    id("boot-library")
}

tasks.build{
    dependsOn(":MediaManagementWebApp:build")
    dependsOn(":MediaManagementSdk:build")
}

group = "org.example.media.management"