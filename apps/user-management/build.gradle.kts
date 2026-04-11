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
    dependsOn(":user-management:webapp:build")
    dependsOn(":user-management:webapp:bootJar")
    dependsOn(":user-management:sdk:build")
    dependsOn(":user-management:sdk:openApiGenerate")
    dependsOn(":user-management:typescript-sdk:publishSdkLocally")
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}