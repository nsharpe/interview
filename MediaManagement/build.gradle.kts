import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("org.openapi.generator") version "7.14.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "org.example.media.management"


tasks.named<BootBuildImage>("bootBuildImage") {
    this.imageName= "media-player/media-management:test"
}

tasks.bootJar{
    archiveFileName = "app.jar"
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")
    apiDocsUrl.set("http://localhost:8082/api-docs")

    customBootRun {
        args.set(listOf("--spring.profiles.active=openapi"))
        args.add("--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
        args.add("--spring.jpa.hibernate.ddl-auto=none")
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
}

tasks.named("generateOpenApiDocs") {
    // This ensures the spec is updated whenever the code changes
}

dependencies {
    implementation(project(":SpringWeb"))
    implementation(project(":Series"))

    // SQL
    implementation("org.example.driver:mysql-driver")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}