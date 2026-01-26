plugins {
    id("web-convention")
}

group = "org.example.public.rest"

tasks.bootBuildImage {
    imageName = "media-player/public-rest-endpoint:test"
}

openApi {
    apiDocsUrl.set("http://localhost:8080/api-docs")
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

    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}