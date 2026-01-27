plugins {
    id("web-sdk")
}

group = "org.example.admin.sdk"

tasks.openApiGenerate {
    dependsOn(":WebApp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("WebApp/build/api-spec.json"))
    basePackage.set("org.example.admin.sdk")
}