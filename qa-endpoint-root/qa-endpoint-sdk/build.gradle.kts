plugins {
    id("web-sdk")
}

group = "org.example.qa"

tasks.openApiGenerate {
    dependsOn(":qa-endpoint-webapp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("qa-endpoint-webapp/build/api-spec.json"))
    basePackage.set("org.example.qa.sdk")
}