import gradle.kotlin.dsl.accessors._bf117e2e4a6f3fe89b8812506a833cd1.testImplementation

plugins {
    id("java-convention")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.bootJar{
    archiveFileName = "app.jar"
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    val dockerFiles = listOf("docker-compose.yml", "docker-compose.fixedport.yml")
        .joinToString(",") { rootProject.projectDir.parentFile.resolve(it).absolutePath }

    systemProperty("spring.docker.compose.file", dockerFiles)
    systemProperty("spring.profiles.active", "local")
}

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
3
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}