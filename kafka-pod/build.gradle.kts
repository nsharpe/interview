plugins {
    id("boot-library")
}

group = "org.example.pod.kafka"

dependencies {
    api("org.example.driver:kafka-driver")
    api("org.example.pod:spring-pod")
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}