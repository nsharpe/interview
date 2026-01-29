plugins {
    id("boot-library")
}

tasks.build{
    dependsOn(":MediaManagementWebApp:build")
    dependsOn(":MediaManagementWebApp:buildJar")
    dependsOn(":media-management-sdk:build")
    dependsOn(":media-management-sdk:openApiGenerate")
}

group = "org.example.media.management"