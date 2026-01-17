tasks.bootRun {
    enabled = false
}

tasks.bootJar {
    enabled = false
}

val testcontainersVersion = "1.21.4"

dependencies {
    // Project dependencies use the project() function
    implementation(project(":PublicRestEndpoint"))
    implementation(project(":MySqlDriver"))
    implementation(project(":Core"))

    implementation("org.springframework.boot:spring-boot-starter")

    runtimeOnly("com.mysql:mysql-connector-j")
    testRuntimeOnly("com.mysql:mysql-connector-j")

    // Using the variable defined above
    implementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))

    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
}