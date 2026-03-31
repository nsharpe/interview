plugins {
    id("boot-library")
}

group = "org.amoeba.example.public.rest"

tasks.spotbugsTest{
    enabled = false
}

tasks.spotbugsMain{
    enabled = false
}

tasks.build{
    dependsOn(":user-management-endpoint-webapp:build")
    dependsOn(":user-management-endpoint-webapp:bootJar")
    dependsOn(":user-management-endpoint-sdk:build")
    dependsOn(":user-management-endpoint-sdk:openApiGenerate")
    dependsOn(":user-management-endpoint-typescript-sdk:publishSdkLocally")
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}