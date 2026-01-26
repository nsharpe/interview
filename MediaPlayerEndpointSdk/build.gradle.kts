plugins {
    id("boot-library")
    id("org.openapi.generator") version "7.14.0"
}

evaluationDependsOnChildren()

group = "org.example.media.player.sdk"

dependencies {
    api(project(":Core"))
    api("org.springframework.boot:spring-boot-starter-webflux")

    api("org.openapitools:jackson-databind-nullable:0.2.6")

    // Dependencies required by the generated SDK code
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
}

val specFile = rootProject.layout.projectDirectory.file("MediaPlayerEndpoint/build/api-spec.json")
val generatedSourcesDir = layout.buildDirectory.dir("generated/sdk")

tasks.openApiGenerate {
    dependsOn(":MediaPlayerEndpoint:generateOpenApiDocs")
}

tasks.spotbugsMain{
    enabled = false
}

tasks.spotbugsTest{
    enabled = false
}

openApiGenerate {
    generatorName.set("java")

    inputSpec.set(specFile.asFile.absolutePath)
    outputDir.set(generatedSourcesDir.map { it.asFile.absolutePath })

    apiPackage.set("org.example.media.player.sdk.api")
    modelPackage.set("org.example.media.player.sdk.models")
    invokerPackage.set("org.example.media.player.sdk.invoker")

    configOptions.set(mapOf(
        "dateLibrary" to "java8",
        "library" to "webclient",
        "serializationLibrary" to "jackson",
        "useJakartaEe" to "true",
        "lombok" to "true"
    ))
}

sourceSets {
    main {
        java {
            srcDir(generatedSourcesDir.map { "${it.asFile.absolutePath}/src/main/java" })
        }
    }
}

tasks.compileJava {
    dependsOn(tasks.openApiGenerate)
}
