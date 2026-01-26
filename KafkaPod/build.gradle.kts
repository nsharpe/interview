plugins {
    id("boot-library")
}

group = "org.example.pod.kafka"

dependencies {
    api("org.example.driver:kafka-driver")
    api(project(":SpringPod"))
}
