plugins {
    id("web-sdk")
}

group = "org.amoeba.example.media.metric"

tasks.openApiGenerate {
    dependsOn(":media-metric-endpoint-webapp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("media-metric-endpoint-webapp/build/api-spec.json"))
    basePackage.set("org.amoeba.example.media.metric.sdk")
}