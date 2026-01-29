plugins {
    id("web-convention")
}

group = "org.example.public.rest"

tasks.bootBuildImage {
    imageName = "media-player/public-rest-endpoint:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")
    apiDocsUrl.set("http://localhost:8080/api-docs")

    customBootRun {
        args.set(listOf("--spring.profiles.active=openapi"))
        args.add("--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
        args.add("--spring.jpa.hibernate.ddl-auto=none")
    }
}


dependencies {
    implementation("org.example.web:spring-web")
    implementation("org.example.business-domain:users")

    // SQL
    implementation("org.example.driver:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}