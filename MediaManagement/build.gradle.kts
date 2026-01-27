import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("web-convention")
}

group = "org.example.media.management"

tasks.named<BootBuildImage>("bootBuildImage") {
    this.imageName= "media-player/media-management:test"
}

openApi {
    outputDir.set(file("build"))
    outputFileName.set("api-spec.json")
    apiDocsUrl.set("http://localhost:8082/api-docs")

    customBootRun {
        args.set(listOf("--spring.profiles.active=openapi"))
        args.add("--spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
        args.add("--spring.jpa.hibernate.ddl-auto=none")
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