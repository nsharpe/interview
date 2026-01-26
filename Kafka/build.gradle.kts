plugins {
    id("boot-library")
}

group = "org.example.kafka"

dependencies {
    api("org.example.core:spring-core")
    api(project(":AvroModel"))

    api("org.springframework.kafka:spring-kafka")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}