plugins {
    id("boot-library")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    api(project(":Core"))

    runtimeOnly("com.mysql:mysql-connector-j")
}