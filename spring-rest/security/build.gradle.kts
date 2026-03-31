plugins {
    id("boot-library")
}

group = "org.amoeba.example.web.security"

dependencies {
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.amoeba.example.drivers:redis-driver")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.15")
}