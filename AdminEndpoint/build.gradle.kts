plugins {
    id("boot-library")
}

tasks.build{
    dependsOn(":AdminWebApp:build")
    dependsOn(":AdminSdk:build")
}

group = "org.example.admin"
