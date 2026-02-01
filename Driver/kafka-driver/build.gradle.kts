plugins {
    id("boot-library")
}

group = "org.example.driver"

dependencies {
    api("org.example.core:spring-core")
    api("org.example.avro:avro-model")

    api("org.springframework.kafka:spring-kafka")
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}
