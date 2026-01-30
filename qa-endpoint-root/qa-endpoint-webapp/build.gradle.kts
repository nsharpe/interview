import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("web-convention")
}

group = "org.example.qa"

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "media-player/qa-endpoints:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

openApi {
    apiDocsUrl.set("http://localhost:8085/api-docs")
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")

    customBootRun {
        systemProperties.set(mapOf(
            "spring.profiles.active" to "openapi"
        ))
    }
}

dependencies {
    implementation("org.example.test.data:test-data")
    implementation("org.example.web:spring-web")

    implementation("io.swagger.core.v3:swagger-annotations-jakarta:2.2.32")
}