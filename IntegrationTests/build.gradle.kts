plugins {
    id("java-convention")
}

tasks.bootRun {
    enabled = false
}

tasks.bootJar {
    enabled = false
}

tasks.assemble {
    gradle.includedBuilds.forEach { includedBuild ->
        dependsOn(includedBuild.task(":build"))
    }
}

val testcontainersVersion = "2.0.3"

dependencies {
    implementation("org.example.core:spring-core")

    implementation("org.apache.commons:commons-pool2")

    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("com.mysql:mysql-connector-j")
    testRuntimeOnly("org.postgresql:postgresql")

    // Using the variable defined above
    testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))

    implementation("org.example.qa:qa-endpoint-sdk")
    testImplementation("org.example.test.data:test-data")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:testcontainers-kafka")
    testImplementation("org.springframework.boot:spring-boot-docker-compose")
    testImplementation("org.testcontainers:mysql")
    testImplementation("com.redis:testcontainers-redis:2.2.4")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
}