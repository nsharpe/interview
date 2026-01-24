plugins {
    `java-library`
}

tasks.bootJar {
    enabled=false
}

tasks.bootRun{
    enabled=false
}

group = "org.example.pod.kafka"

dependencies {
    api(project(":Kafka"))
    api(project(":SpringPod"))
}
