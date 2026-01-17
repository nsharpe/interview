group = "org.example.public.rest"

dependencies {
    implementation(project(":SpringWeb"))
    implementation(project(":Users"))

    // SQL
    implementation(project(":MySqlDriver"))
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}