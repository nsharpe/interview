plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":media-metric-endpoint:media-metric-endpoint-webapp:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("media-metric-endpoint/media-metric-endpoint-webapp/build/api-spec.json"))
    npmName.set("media-metric-media-player-client")
}