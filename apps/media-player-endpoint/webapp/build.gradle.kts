import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("web-convention")
}

group = "org.amoeba.example.media.player"

base {
    archivesName = "media-player-webapp"
}


configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")
    apiDocsUrl.set("http://localhost:8086/api-docs")

    customBootRun {
        args.set(listOf("--spring.profiles.active=openapi"))
        args.add("--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
        args.add("--spring.jpa.hibernate.ddl-auto=none")
    }
}

dependencies {
    implementation("org.amoeba.example.spring.util:spring-web")
    implementation("org.amoeba.example.avro:avro-model")
    implementation("org.amoeba.example.drivers:kafka-driver")

    implementation("io.swagger.core.v3:swagger-annotations-jakarta:2.2.32")
}