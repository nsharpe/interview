plugins {
    id("web-sdk")
}

group = "org.example.admin"

tasks.openApiGenerate {
    dependsOn(":admin-endpoint-web-app:generateOpenApiDocs")
}

val generatedSourcesDir = layout.buildDirectory.dir("generated/sdk")

sourceSets {
    main {
        java {
            srcDir(generatedSourcesDir.map { "${it.asFile.absolutePath}/src/main/java" })
        }
    }
}

sdkConfig {
    specFile.set(rootProject.layout.projectDirectory.file("admin-endpoint-web-app/build/api-spec.json"))
    basePackage.set("org.example.admin.sdk")
}