plugins {
    id("web-convention")
}

group = "org.example.admin"

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
    implementation("org.example.web:spring-web")
    implementation("org.example.business-domain:users")

    implementation("org.example.media.management:media-management-sdk")
    implementation("org.example.public.rest:public-rest-endpoint-sdk")

    // SQL
    implementation("org.example.driver:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}