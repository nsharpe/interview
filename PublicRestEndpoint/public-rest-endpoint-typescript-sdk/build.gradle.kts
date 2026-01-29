plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":public-rest-endpoint-webapp:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("public-rest-endpoint-webapp/build/api-spec.json"))
    npmName.set("media-player-public-rest-client")
}