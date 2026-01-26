plugins {
    id("org.openapi.generator") version "7.14.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "org.example.public.rest"

tasks.bootBuildImage {
    imageName = "media-player/public-rest-endpoint:test"
}

tasks.bootJar {
    archiveFileName = "app.jar"
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")
    apiDocsUrl.set("http://localhost:8080/api-docs")

    customBootRun{
        args.set(listOf("--spring.profiles.active=openapi"))
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

tasks.forkedSpringBootRun{
    dependsOn(projectJarPaths)
}

dependencies {
    implementation(project(":SpringWeb"))
    implementation(project(":Users"))
    implementation(project(":Security"))

    // SQL
    implementation("org.example.driver:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")

    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}