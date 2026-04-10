plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":admin-endpoint:webapp:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("admin-endpoint/webapp/build/api-spec.json"))
    npmName.set("media-player-admin-client")
}