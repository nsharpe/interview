plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":media-comment:webapp:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("media-comment/webapp/build/api-spec.json"))
    npmName.set("media-comment-client")
}