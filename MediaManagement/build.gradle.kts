group = "org.example.media.management"

tasks.bootBuildImage {
    imageName = "media-player/media-management:test"
}

tasks.bootJar{
    archiveFileName = "media-management-app.jar"
}

dependencies {
    implementation(project(":SpringWeb"))
    implementation(project(":Series"))

    // SQL
    implementation(project(":MySqlDriver"))
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}