import com.github.spotbugs.snom.SpotBugsTask
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins{
    id("boot-library")
    id("org.openapi.generator")
    id("io.spring.dependency-management")
}

evaluationDependsOnChildren()

val sdkConfig = extensions.create<SdkExtension>("sdkConfig")

tasks.withType<SpotBugsTask>().configureEach {
    enabled = false
}

tasks.withType<GenerateTask>().configureEach {
    generatorName.set("java")

    println("SDK input " + sdkConfig.specFile.get().asFile.absolutePath)

    inputSpec.set(sdkConfig.specFile.get().asFile.absolutePath)
    outputDir.set(layout.buildDirectory.dir("generated/sdk").map { it.asFile.absolutePath })

    apiPackage.set(sdkConfig.basePackage.map { "$it.api" })
    modelPackage.set(sdkConfig.basePackage.map { "$it.models" })
    invokerPackage.set(sdkConfig.basePackage.map { "$it.invoker" })

    configOptions.set(mapOf(
        "dateLibrary" to "java8",
        "library" to "webclient",
        "serializationLibrary" to "jackson",
        "useJakartaEe" to "true",
        "lombok" to "true"
    ))
}

val generatedSourcesDir = objects.directoryProperty()

val openApiTask = tasks.named<GenerateTask>("openApiGenerate") {
    configOptions.put("sourceFolder", "src/main/java")
}

dependencies {

    api("org.springframework.boot:spring-boot-starter-webflux")

    api("org.openapitools:jackson-databind-nullable:0.2.6")

    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.moshi:moshi-kotlin:1.15.0")
    api("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    api("jakarta.annotation:jakarta.annotation-api:2.1.1")
}

val openApiGenerate = project.tasks.named("openApiGenerate")

project.tasks.named("compileJava") {
    dependsOn(openApiGenerate)
}
