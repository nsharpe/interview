import com.github.gradle.node.npm.task.NpmTask

plugins {
    id("java-convention")
    id("com.github.node-gradle.node") version "7.0.2"
}

node {
    download.set(true)
    version.set("20.11.0")

    workDir.set(layout.projectDirectory.dir(".gradle/node"))
}

tasks.build {
    dependsOn("npmInstall")
}

tasks.register<NpmTask>("npmStart") {
    dependsOn("npmInstall")
    args.set(listOf("run", "start"))
}