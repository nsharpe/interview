plugins {
    id("web-convention")
}

group = "org.example.media.management"

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
    implementation("org.example.web:spring-web")
    implementation("org.example.business-domain:series")

    // SQL
    implementation("org.example.driver:mysql-driver")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}