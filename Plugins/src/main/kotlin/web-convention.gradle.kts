
plugins {
    id("boot-convention")
    id("web-documentation")
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

tasks.named("generateOpenApiDocs") {
    // This ensures the spec is updated whenever the code changes
}

val projectJarPaths = configurations.implementation.map { config ->
    config.allDependencies
        .withType<ProjectDependency>()
        .map { "${it.path}:jar" }
}

tasks.named("forkedSpringBootRun"){
    dependsOn(projectJarPaths)
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")

    customBootRun {
        args.set(listOf("--spring.profiles.active=openapi"))
        args.add("--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
        args.add("--spring.jpa.hibernate.ddl-auto=none")
    }
}