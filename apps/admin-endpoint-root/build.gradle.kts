plugins {
    id("java-convention")
}

group = "org.amoeba.example.admin"

tasks.spotbugsTest{
    enabled = false
}

tasks.spotbugsMain{
    enabled = false
}

tasks.build{
    dependsOn(":admin-endpoint-root:admin-endpoint-web-app:build")
    dependsOn(":admin-endpoint-root:admin-endpoint-web-app:bootJar")
    dependsOn(":admin-endpoint-root:admin-sdk:build")
    dependsOn(":admin-endpoint-root:admin-sdk:openApiGenerate")
    dependsOn(":admin-endpoint-root:admin-typescript-sdk:publishSdkLocally")
}

