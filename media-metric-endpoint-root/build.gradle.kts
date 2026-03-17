plugins{
    id("java-convention")
}

group = "com.example"

tasks.build{
    dependsOn(":media-metric-endpoint-webapp:build")
    dependsOn(":media-metric-endpoint-webapp:bootJar")
    dependsOn(":media-metric-endpoint-sdk:build")
    dependsOn(":media-metric-endpoint-sdk:openApiGenerate")
    dependsOn(":media-metric-typescript-sdk:publishSdkLocally")
}

tasks.register("cleanAll") {
    dependsOn(":media-metric-endpoint-webapp:clean")
    dependsOn(":media-metric-endpoint-sdk:clean")
    dependsOn(":media-metric-typescript-sdk:clean")
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}