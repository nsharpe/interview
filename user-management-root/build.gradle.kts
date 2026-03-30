plugins {
    id("boot-library")
}

group = "org.example.public.rest"

tasks.spotbugsTest{
    enabled = false
}

tasks.spotbugsMain{
    enabled = false
}

tasks.build{
    dependsOn(":user-management-endpoint-webapp:build")
    dependsOn(":user-management-endpoint-webapp:bootJar")
    dependsOn(":public-rest-endpoint-sdk:build")
    dependsOn(":public-rest-endpoint-sdk:openApiGenerate")
    dependsOn(":public-rest-endpoint-typescript-sdk:publishSdkLocally")
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}