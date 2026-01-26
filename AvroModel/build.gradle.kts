plugins {
    id("java-convention")
    `java-library`
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "org.example.avro"

dependencies {
    api("org.apache.avro:avro:1.12.1")

    // Source: https://mvnrepository.com/artifact/io.confluent/kafka-avro-serializer
    api("io.confluent:kafka-avro-serializer:8.0.3")

    api("com.fasterxml.jackson.dataformat:jackson-dataformat-avro")
}

tasks.spotbugsTest{
    enabled = false
}

tasks.spotbugsMain{
    enabled = false
}

tasks.generateAvroJava {
    source("src/main/avro")
}

sourceSets {
    main {
        java {
            srcDir("build/generated-main-avro-java")
        }
    }
}