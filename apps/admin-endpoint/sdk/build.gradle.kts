plugins {
    id("web-sdk")
}

group = "org.amoeba.example.admin"

base {
    archivesName = "admin-sdk"
}

tasks.openApiGenerate {
    dependsOn(":admin-endpoint:webapp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("admin-endpoint/webapp/build/api-spec.json"))
    basePackage.set("org.amoeba.example.admin.sdk")
}