plugins {
    id("web-sdk")
}

group = "org.example.public.rest"

tasks.openApiGenerate {
    dependsOn(":public-rest-endpoint-webapp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("public-rest-endpoint-webapp/build/api-spec.json"))
    basePackage.set("org.example.publicrest.sdk")
}