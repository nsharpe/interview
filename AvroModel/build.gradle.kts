plugins {
    `java-library`
    id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "org.example.avro"

dependencies {
    implementation("org.apache.avro:avro:1.11.3")
}

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
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