plugins {
    `java-library`
}

group = "org.example.pod"

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
}

dependencies {
    api( "org.springframework.boot:spring-boot-starter-web")
    api( "org.springframework.boot:spring-boot-starter-actuator")
}