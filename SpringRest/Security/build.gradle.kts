plugins {
    id("boot-library")
}

group = "org.example.web.security"

dependencies {
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.example.driver:redis-driver")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.15")
}