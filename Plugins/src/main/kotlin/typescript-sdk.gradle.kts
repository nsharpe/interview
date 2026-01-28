import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins{
    id("java-convention")
    id("org.openapi.generator")
    id("io.spring.dependency-management")
}

evaluationDependsOnChildren()

val sdkConfig = extensions.create<TypeScriptSdkExtension>("sdkConfig")

tasks.withType<GenerateTask>().configureEach {

    generatorName = "typescript-axios"

    inputSpec.set(sdkConfig.specFile.get().asFile.absolutePath)
    outputDir.set(
        layout.projectDirectory
            .dir("./build/src/generated")
            .asFile
            .absolutePath
    )

    println("Typescript SDK output " + outputDir.get())


    configOptions.set(mapOf(
        "npmName" to "media-player-admin-client",
        "supportsES6" to "true",
        "npmVersion" to "0.0.1"
    ))
}

val installSdkDeps by tasks.registering(Exec::class) {
    group = "publishing"
    description = "Installs dependencies in the generated SDK"

    dependsOn(tasks.withType<GenerateTask>())

    workingDir(layout.projectDirectory.dir("build/src/generated"))
    commandLine("sh", "-c", "npm install")
}

val publishSdkLocally by tasks.registering(Exec::class) {
    group = "publishing"
    description = "Links the SDK locally using npm link"

    dependsOn(installSdkDeps)

    workingDir(layout.projectDirectory.dir("build/src/generated"))
    commandLine("sh", "-c", "npm link")
}