plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":media-player-endpoint-webapp:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("media-player-endpoint-webapp/build/api-spec.json"))
    npmName.set("media-player-media-player-client")
}