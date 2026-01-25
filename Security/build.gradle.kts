plugins {
    `java-library`
}

group = "org.example.security"

tasks.bootJar{
    enabled=false
}

tasks.bootRun{
    enabled=false
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
    api(project(":SpringWeb"))
    api("org.springframework.boot:spring-boot-starter-security")
    api(project(":Redis"))
}