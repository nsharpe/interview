plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":qa-endpoint-webapp:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("qa-endpoint-webapp/build/api-spec.json"))
    npmName.set("media-player-qa-client")
}