group = "org.example.public.rest"

dependencies {
    implementation(project(":Core"))
    implementation(project(":SpringPod"))
    implementation(project(":Users"))

    // Web and documentation
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // SQL
    implementation(project(":MySqlDriver"))
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}