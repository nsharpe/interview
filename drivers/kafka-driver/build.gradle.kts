plugins {
    id("boot-library")
}

group = "org.example.drivers"

dependencies {
    api("org.example.core:java-core")
    api("org.example.avro:avro-model")

    api("org.springframework.kafka:spring-kafka")
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}
