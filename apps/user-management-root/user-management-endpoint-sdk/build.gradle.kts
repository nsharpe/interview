plugins {
    id("web-sdk")
}

group = "org.amoeba.example.apps.user_management.sdk"

tasks.openApiGenerate {
    dependsOn(":user-management-root:user-management-endpoint-webapp:generateOpenApiDocs")
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
    specFile.set(rootProject.layout.projectDirectory.file("user-management-root/user-management-endpoint-webapp/build/api-spec.json"))
    basePackage.set("org.amoeba.example.apps.user-management.sdk")
}