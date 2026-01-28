plugins {
    id("boot-library")
}

group = "org.example.driver"

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    api("org.example.core:spring-core")

    runtimeOnly("com.mysql:mysql-connector-j")
}