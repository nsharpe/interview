plugins {
    id("web-convention")
}

group = "org.amoeba.example.media.management"

base {
    archivesName = "webapp"
}

tasks.bootBuildImage {
    imageName= "media-player/media-management:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")
    apiDocsUrl.set("http://localhost:8082/api-docs")

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
    implementation("org.amoeba.example.libs:series")

    // SQL
    implementation("org.amoeba.example.drivers:mysql-driver")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}