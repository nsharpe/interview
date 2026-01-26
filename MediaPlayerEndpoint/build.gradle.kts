import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("web-convention")
}

group = "org.example.media.player"

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "media-player/media-player-endpoints:test"
}

openApi {
    apiDocsUrl.set("http://localhost:8083/api-docs")
}

dependencies {
    implementation("org.example.web:spring-web")
    implementation("org.example.avro:avro-model")
    implementation("org.example.driver:kafka-driver")

    implementation("io.swagger.core.v3:swagger-annotations-jakarta:2.2.32")
}