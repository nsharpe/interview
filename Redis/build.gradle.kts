plugins {
    `java-library`
}

group = "org.example.redis"

tasks.bootJar {
    enabled = false
}

tasks.bootRun {
    enabled = false
}

dependencies {
    api(project(":Core"))
    api("org.springframework.boot:spring-boot-starter-data-redis")
}