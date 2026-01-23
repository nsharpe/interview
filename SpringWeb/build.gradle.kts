plugins{
    `java-library`
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "org.example.web"

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
}

val projectJarPaths = configurations.implementation.map { config ->
    config.allDependencies
        .withType<ProjectDependency>()
        .map { "${it.path}:jar" }
}

tasks.jar{
    dependsOn(projectJarPaths)
}

dependencies {
    api(project(":SpringPod"))

    // Web and documentation
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.15")
}
