import gradle.kotlin.dsl.accessors._bf117e2e4a6f3fe89b8812506a833cd1.testImplementation

plugins {
    id("java-convention")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.bootJar{
    archiveFileName = "app.jar"
}

val copyDockerConfig = tasks.register<Copy>("copyDockerConfig") {
    // Look two levels up for the source files
    from(file("../../docker-compose.yml"))
    from(file("../../docker-compose.fixedport.yml"))

    into(layout.buildDirectory.dir("docker-config"))
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    dependsOn(copyDockerConfig)
    val dockerConfigDir = layout.buildDirectory.dir("docker-config")
    val dockerFiles = "docker-compose.yml,docker-compose.fixedport.yml"
        .split(",")
        .joinToString(",") { "${dockerConfigDir.get().asFile.absolutePath}/$it" }
    systemProperty("spring.docker.compose.file", dockerFiles)
}

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
3
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}