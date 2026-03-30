plugins {
    id("web-sdk")
}

group = "org.example.public.rest"

tasks.openApiGenerate {
    dependsOn(":user-management-endpoint-webapp:generateOpenApiDocs")
}

val generatedSourcesDir = layout.buildDirectory.dir("generated/sdk")

sourceSets {
    main {
        java {
            srcDir(generatedSourcesDir.map { "${it.asFile.absolutePath}/src/main/java" })
        }
    }
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("user-management-endpoint-webapp/build/api-spec.json"))
    basePackage.set("org.example.publicrest.sdk")
}