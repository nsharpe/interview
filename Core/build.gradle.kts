plugins {
    `java-library`
}

tasks.test {
    useJUnitPlatform()
    failOnNoDiscoveredTests = false
}

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
}

dependencies {
    api("org.modelmapper:modelmapper:3.2.6")

    api("org.springframework.boot:spring-boot-starter")

    api("org.springframework.retry:spring-retry")
    api("org.springframework:spring-aspects")

    api("com.fasterxml.jackson.core:jackson-databind:2.20.1")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.20.1")
}