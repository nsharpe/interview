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
    dependsOn(":admin-endpoint:admin-endpoint-web-app:build")
    dependsOn(":admin-endpoint:admin-endpoint-web-app:bootJar")
    dependsOn(":admin-endpoint:sdk:build")
    dependsOn(":admin-endpoint:sdk:openApiGenerate")
    dependsOn(":admin-endpoint:admin-typescript-sdk:publishSdkLocally")
}

