plugins {
    id("boot-library")
}

group = "org.amoeba.example.pod.kafka"

dependencies {
    api("org.amoeba.example.drivers:kafka-driver")
    api("org.amoeba.example.pod:spring-pod")
}

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}