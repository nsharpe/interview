plugins {
    id("web-sdk")
}

group = "org.example.admin.sdk"

tasks.openApiGenerate {
    dependsOn(":MediaManagementWebApp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("MediaManagementWebApp/build/api-spec.json"))
    basePackage.set("org.example.media.management.sdk")
}
