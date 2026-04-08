plugins {
    id("web-convention")
}

group = "org.amoeba.example.admin"

tasks.bootBuildImage {
    imageName= "media-player/admin-endpoint:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")
    apiDocsUrl.set("http://localhost:8084/admin/api-docs")

    customBootRun {
        systemProperties.set(mapOf(
            "spring.profiles.active" to "openapi",
            "spring.jpa.database-platform" to "org.hibernate.dialect.H2Dialect",
            "spring.jpa.hibernate.ddl-auto" to "none"
        ))
    }
}

dependencies {
    implementation("org.amoeba.example.web:spring-web")
    implementation("org.amoeba.example.libs:users")

    implementation("org.amoeba.example.media.management:media-management-sdk")
    implementation(project(":user-management:user-management-endpoint-sdk"))

    // SQL
    implementation("org.amoeba.example.drivers:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}