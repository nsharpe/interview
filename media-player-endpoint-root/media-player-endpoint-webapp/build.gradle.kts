import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("web-convention")
}

group = "org.example.media.player"

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "media-player/media-player-endpoints:test"
}

openApi {
    apiDocsUrl.set("http://localhost:8086/api-docs")
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")

    customBootRun {
        args.set(listOf("--spring.profiles.active=openapi"))
        args.add("--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
        args.add("--spring.jpa.hibernate.ddl-auto=none")
    }
}

dependencies {
    implementation("org.example.web:spring-web")
    implementation("org.example.avro:avro-model")
    implementation("org.example.driver:kafka-driver")

    implementation("io.swagger.core.v3:swagger-annotations-jakarta:2.2.32")
}