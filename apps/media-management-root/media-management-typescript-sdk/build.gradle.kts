plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":media-management-root:media-management-webapp:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("media-management-root/media-management-webapp/build/api-spec.json"))
    npmName.set("media-player-media-management-client")
}