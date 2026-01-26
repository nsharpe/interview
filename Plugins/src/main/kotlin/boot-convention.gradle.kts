
plugins {
    id("java-convention")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.bootJar{
    archiveFileName = "app.jar"
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    systemProperty("spring.docker.compose.file",
        rootProject.file("docker-compose.yml").absolutePath +","+
                rootProject.file("docker-compose.fixedport.yml").absolutePath)
}