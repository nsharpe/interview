
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

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
