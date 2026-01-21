tasks.bootRun {
    enabled = false
}

tasks.bootJar {
    enabled = false
}

tasks.assemble{
    dependsOn(":TestData:jar")
}

tasks.test{
    dependsOn(":PublicRestEndpoint:bootJar")
    dependsOn(":MediaManagement:bootJar")
}

val testcontainersVersion = "1.21.4"

dependencies {
    // Project dependencies use the project() function
    implementation(project(":PublicRestEndpoint"))
    implementation(project(":MySqlDriver"))
    implementation(project(":Core"))
    implementation(project(":Users"))

    runtimeOnly("com.mysql:mysql-connector-j")
    testRuntimeOnly("com.mysql:mysql-connector-j")

    // Using the variable defined above
    implementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))

    testImplementation(project(":TestData"))
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
}