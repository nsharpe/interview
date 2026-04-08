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

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}

group = "org.amoeba.example.media.management"