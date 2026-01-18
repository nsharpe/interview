group = "org.example.public.rest"

tasks.bootBuildImage {
    imageName.set("media-player/public-rest-endpoint:test")
}

tasks.bootJar {
    archiveFileName = "public-rest-endpoint-app.jar"
}

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