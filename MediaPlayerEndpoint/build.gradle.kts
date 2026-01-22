plugins {
    id("org.openapi.generator") version "7.14.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "org.example.media.player"

tasks.bootBuildImage {
    imageName = "media-player/media-player-endpoints:test"
}

tasks.bootJar{
    archiveFileName = "app.jar"
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")
    apiDocsUrl.set("http://localhost:8083/api-docs")

    customBootRun {
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
    args.add("--spring.profiles.active=openapi")
    args.add("--Dserver.port=8083")
}

tasks.named("generateOpenApiDocs") {
    // This ensures the spec is updated whenever the code changes
}

dependencies {
    implementation(project(":SpringWeb"))
    implementation(project(":AvroModel"))
    implementation(project(":Kafka"))
}