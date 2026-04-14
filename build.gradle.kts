plugins {
    id("java-convention")
    id("org.springframework.boot") version "3.5.10" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    group = "org.amoeba.example"
    version = "1.0-SNAPSHOT"
}

tasks.test {
    enabled = false
}

tasks.assemble {
    dependsOn(":copyEnv")
}

tasks.register("npmStart"){
    val uiBuild = gradle.includedBuild("media-player-ui")
    dependsOn(uiBuild.task(":npmStart"))
}

tasks.register("cleanAll"){
    dependsOn(gradle.includedBuilds.map {
        it.task(":clean") })
}

subprojects {
    apply{
        plugin("java-convention")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        maxHeapSize = "512m"
    }


}

tasks.register("copyEnv") {
    group = "setup"
    description = "Copies example.env to .env if .env does not exist."

    val exampleFile = layout.projectDirectory.file("example.env").asFile
    val envFile = layout.projectDirectory.file(".env").asFile

    doLast {
        if (!envFile.exists()) {
            if (exampleFile.exists()) {
                exampleFile.copyTo(envFile)
                logger.lifecycle("ran `cp example.env .env`")
            }
        } else {
            logger.lifecycle(".env already exists. Skipping copy.")
        }
    }
}