plugins{
    id("java-convention")
}

group = "org.amoeba.example"

tasks.build{
    dependsOn(":media-metric-endpoint:webapp:build")
    dependsOn(":media-metric-endpoint:webapp:bootJar")
    dependsOn(":media-metric-endpoint:sdk:build")
    dependsOn(":media-metric-endpoint:sdk:openApiGenerate")
    dependsOn(":media-metric-endpoint:typescript-sdk:publishSdkLocally")
}
