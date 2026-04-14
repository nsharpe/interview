plugins {
    id("web-sdk")
}

group = "org.amoeba.example.app.media.comment"


base {
    archivesName = "media-comment-sdk"
}

tasks.openApiGenerate {
    dependsOn(":media-comment:webapp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("media-comment/webapp/build/api-spec.json"))
    basePackage.set("org.amoeba.example.app.media.comment.sdk")
}
