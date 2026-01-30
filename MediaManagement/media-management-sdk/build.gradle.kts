plugins {
    id("web-sdk")
}

group = "org.example.media.management"

tasks.openApiGenerate {
    dependsOn(":media-management-webapp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("media-management-webapp/build/api-spec.json"))
    basePackage.set("org.example.media.management.sdk")
}
