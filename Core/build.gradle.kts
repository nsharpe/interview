plugins {
    id("boot-library")
}

group="org.example.core"

tasks.processResources{
    from(file("../docker-compose.yml"))
    from(file("../docker-compose.fixedport.yml"))
    into("build/resources/main/docker")
}

dependencies {
    api("org.modelmapper:modelmapper:3.2.6")

    api("org.springframework.boot:spring-boot-starter")

    api("org.springframework.retry:spring-retry")
    api("org.springframework:spring-aspects")

    api("com.fasterxml.jackson.core:jackson-databind:2.20.1")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.20.1")

    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}