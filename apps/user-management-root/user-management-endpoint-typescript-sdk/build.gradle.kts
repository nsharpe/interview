plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":user-management-root:user-management-endpoint-webapp:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("user-management-root/user-management-endpoint-webapp/build/api-spec.json"))
    npmName.set("media-player-public-rest-client")
}