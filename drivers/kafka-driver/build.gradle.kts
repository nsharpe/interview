plugins {
    id("boot-library")
}

group = "org.amoeba.example.drivers"

dependencies {
    api("org.amoeba.example.core:java-core")
    api("org.amoeba.example.avro:avro-model")

    api("org.springframework.kafka:spring-kafka")
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}
