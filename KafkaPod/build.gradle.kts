plugins {
    id("boot-library")
}

group = "org.example.pod.kafka"

dependencies {
    api(project(":Kafka"))
    api(project(":SpringPod"))
}
