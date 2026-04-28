import java.net.ServerSocket

plugins {
    id("boot-convention")
    id("web-documentation")
}

val openApiPort = ServerSocket(0).use { it.localPort }

val openApiDocsPath by extra("/api-docs")

afterEvaluate {
    openApi {
        outputDir.set(file("build"))
        outputFileName.set("api-spec.json")
        apiDocsUrl.set("http://localhost:$openApiPort$openApiDocsPath")

        customBootRun {
            systemProperties.set(mapOf(
                "server.port" to openApiPort.toString(),
                "management.server.port" to openApiPort.toString(),
                "spring.profiles.active" to "openapi",
                "spring.jpa.database-platform" to "org.hibernate.dialect.H2Dialect",
                "spring.jpa.hibernate.ddl-auto" to "none",
            ))
        }
    }
}

tasks.jar{
    dependsOn("generateOpenApiDocs")
}

tasks.bootJar{
    dependsOn("generateOpenApiDocs")
}

tasks.spotbugsMain{
    dependsOn("generateOpenApiDocs")
}

tasks.compileTestJava{
    dependsOn("generateOpenApiDocs")
}

val projectJarPaths = configurations.implementation.map { config ->
    config.allDependencies
        .withType<ProjectDependency>()
        .map { "${it.path}:jar" }
}

tasks.named("forkedSpringBootRun"){
    dependsOn(projectJarPaths)
}
