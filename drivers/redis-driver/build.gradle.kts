plugins {
    id("boot-library")
}

group = "org.amoeba.example.drivers"

dependencies {
    api("org.amoeba.example.core:java-core")
    api("org.springframework.boot:spring-boot-starter-data-redis")
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}