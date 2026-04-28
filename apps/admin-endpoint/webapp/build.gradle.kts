plugins {
    id("web-convention")
}

group = "org.amoeba.example.admin"

base {
    archivesName = "admin-webapp"
}

tasks.bootBuildImage {
    imageName= "media-player/admin-endpoint:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

val openApiDocsPath by extra("/admin/api-docs")

dependencies {
    implementation("org.amoeba.example.spring.util:spring-web")
    implementation("org.amoeba.example.libs:users")

    implementation("org.amoeba.example.media.management:sdk")
    implementation(project(":user-management:sdk"))

     // SQL
    implementation("org.amoeba.example.drivers:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}
