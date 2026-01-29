tasks.bootRun {
    enabled = false
}

tasks.bootJar {
    enabled = false
}

tasks.test{
    dependsOn(gradle.includedBuild("SpringRest").task(":bootJar"))
    dependsOn(gradle.includedBuild("AdminEndpoint").task(":build"))
    dependsOn(gradle.includedBuild("MediaManagement").task(":MediaManagementWebApp:bootJar"))
    dependsOn(gradle.includedBuild("PublicRestEndpoint").task(":build"))
    dependsOn(":MediaPlayerEndpoint:bootJar")
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

    testImplementation(project(":TestData"))
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:testcontainers-kafka")
    testImplementation("org.springframework.boot:spring-boot-docker-compose")
    testImplementation("org.testcontainers:mysql")
    testImplementation("com.redis:testcontainers-redis:2.2.4")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
}