plugins {
    id("boot-library")
}

tasks.build{
    dependsOn(":media-management-webapp:build")
    dependsOn(":media-management-webapp:bootJar")
    dependsOn(":media-management-sdk:build")
    dependsOn(":media-management-sdk:openApiGenerate")
    dependsOn(":media-management-typescript-sdk:publishSdkLocally")
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}

group = "org.example.media.management"