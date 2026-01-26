plugins {
    id("boot-library")
}

group = "org.example.redis"

tasks.bootJar {
    enabled = false
}

tasks.bootRun {
    enabled = false
}

dependencies {
    api("org.example.core:spring-core")
    api("org.springframework.boot:spring-boot-starter-data-redis")
}