plugins {
    id("web-sdk")
}

group = "org.example.admin.sdk"

tasks.openApiGenerate {
    dependsOn(":AdminWebApp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("AdminWebApp/build/api-spec.json"))
    basePackage.set("org.example.admin.sdk")
}