plugins {
    id("web-sdk")
}

group = "org.example.public.rest.sdk"

tasks.openApiGenerate {
    dependsOn(":PublicRestEndpointWebApp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("PublicRestEndpointWebApp/build/api-spec.json"))
    basePackage.set("org.example.publicrest.sdk")
}