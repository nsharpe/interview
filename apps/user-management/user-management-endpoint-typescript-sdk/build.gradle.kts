plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":user-management:webapp:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("user-management/webapp/build/api-spec.json"))
    npmName.set("media-player-public-rest-client")
}