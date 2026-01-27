plugins {
    id("boot-library")
}

tasks.build{
    dependsOn(":WebApp:build")
    dependsOn(":Sdk:build")
}

group = "org.example.admin"
