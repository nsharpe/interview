plugins {
    `java-library`
}

group = "org.example.pod"
version = "1.0-SNAPSHOT"

dependencies {
    api( "org.springframework.boot:spring-boot-starter")
    api( "org.springframework.boot:spring-boot-starter-web")
    api( "org.springframework.boot:spring-boot-starter-actuator")
}