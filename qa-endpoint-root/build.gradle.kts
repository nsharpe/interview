plugins {
    id("java-convention")
}

group = "org.example.qa"

tasks.spotbugsTest{
    enabled = false
}

tasks.spotbugsMain{
    enabled = false
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}

tasks.build{
    dependsOn(":qa-endpoint-webapp:build")
    dependsOn(":qa-endpoint-webapp:bootJar")
    dependsOn(":qa-endpoint-sdk:build")
    dependsOn(":qa-endpoint-sdk:openApiGenerate")
    dependsOn(":qa-endpoint-typescript-sdk:publishSdkLocally")
}