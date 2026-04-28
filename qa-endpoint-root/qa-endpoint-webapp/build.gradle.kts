import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("web-convention")
}

group = "org.amoeba.example.qa"

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "media-player/qa-endpoints:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

dependencies {
    implementation("org.amoeba.example.test.data:test-data")
    implementation("org.amoeba.example.spring.util:spring-web")

    implementation("io.swagger.core.v3:swagger-annotations-jakarta:2.2.32")
}
