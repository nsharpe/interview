plugins {
    id("boot-library")
}

group = "org.example.driver"

dependencies {
    api("org.example.core:spring-core")
    api("org.springframework.boot:spring-boot-starter-data-redis")
}