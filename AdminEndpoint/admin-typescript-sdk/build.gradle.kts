plugins {
    id("typescript-sdk")
}

tasks.openApiGenerate {
    dependsOn(":admin-endpoint-web-app:generateOpenApiDocs")
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("admin-endpoint-web-app/build/api-spec.json"))
}