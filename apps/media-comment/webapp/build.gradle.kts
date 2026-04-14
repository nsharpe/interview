plugins {
    id("web-convention")
}


group = "org.amoeba.example.app.media.comment"


tasks.bootBuildImage {
    imageName= "media-player/media-management:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")
    apiDocsUrl.set("http://localhost:8088/api-docs")

    customBootRun {
        systemProperties.set(mapOf(
            "spring.profiles.active" to "openapi",
            "spring.jpa.database-platform" to "org.hibernate.dialect.H2Dialect",
            "spring.jpa.hibernate.ddl-auto" to "none"
        ))
    }
}

dependencies {
    implementation("org.amoeba.example.spring.util:spring-web")
    implementation("org.amoeba.example.spring.util:webflux")
    implementation("org.amoeba.example.libs:comments")

    // SQL
    implementation("org.amoeba.example.drivers:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
}