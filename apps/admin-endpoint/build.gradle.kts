plugins {
    id("java-convention")
}

group = "org.amoeba.example.admin"

base {
    archivesName = "admin-sdk"
}

tasks.spotbugsTest{
    enabled = false
}

tasks.spotbugsMain{
    enabled = false
}

tasks.build{
    dependsOn(":admin-endpoint:webapp:build")
    dependsOn(":admin-endpoint:webapp:bootJar")
    dependsOn(":admin-endpoint:sdk:build")
    dependsOn(":admin-endpoint:sdk:openApiGenerate")
    dependsOn(":admin-endpoint:admin-typescript-sdk:publishSdkLocally")
}

