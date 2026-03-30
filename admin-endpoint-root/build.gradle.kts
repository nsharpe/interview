plugins {
    id("java-convention")
}

group = "org.example.admin"

tasks.spotbugsTest{
    enabled = false
}

tasks.spotbugsMain{
    enabled = false
}

tasks.build{
    dependsOn(":admin-endpoint-web-app:build")
    dependsOn(":admin-endpoint-web-app:bootJar")
    dependsOn(":admin-sdk:build")
    dependsOn(":admin-sdk:openApiGenerate")
    dependsOn(":admin-typescript-sdk:publishSdkLocally")
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}
