plugins{
    id("java-convention")
}

group = "org.amoeba.example"

tasks.build{
    dependsOn(":media-metric-endpoint-root:media-metric-endpoint-webapp:build")
    dependsOn(":media-metric-endpoint-root:media-metric-endpoint-webapp:bootJar")
    dependsOn(":media-metric-endpoint-root:media-metric-endpoint-sdk:build")
    dependsOn(":media-metric-endpoint-root:media-metric-endpoint-sdk:openApiGenerate")
    dependsOn(":media-metric-endpoint-root:media-metric-typescript-sdk:publishSdkLocally")
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}