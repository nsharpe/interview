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
        "npmName" to sdkConfig.npmName.get(),
        "supportsES6" to "true",
        "npmVersion" to "0.0.1"
    ))
}

val cleanSdk by tasks.registering(Exec::class) {
    group = "publishing"
    description = "Installs dependencies in the generated SDK"

    dependsOn(tasks.withType<GenerateTask>())

    commandLine("sh", "-c", "npm uninstall -g " + sdkConfig.npmName.get())
}

val installSdkDeps by tasks.registering(Exec::class) {
    group = "publishing"
    description = "Installs dependencies in the generated SDK"

    dependsOn(tasks.withType<GenerateTask>())

    workingDir(layout.projectDirectory.dir("build/src/generated"))
    commandLine("sh", "-c", "npm install --ignore-scripts --no-package-lock")
}


val buildSdk by tasks.registering(Exec::class) {
    group = "publishing"
    description = "Compiles the TypeScript SDK"

    dependsOn(installSdkDeps) // Ensure install happens first!

    workingDir(layout.projectDirectory.dir("build/src/generated"))
    commandLine("sh", "-c", "npm run build --ignore-scripts --no-package-lock")
}

val publishSdkLocally by tasks.registering(Exec::class) {
    group = "publishing"
    description = "Links the SDK locally using npm link"

    dependsOn(buildSdk)

    workingDir(layout.projectDirectory.dir("build/src/generated"))
    commandLine("sh", "-c", "npm link --ignore-scripts")
}