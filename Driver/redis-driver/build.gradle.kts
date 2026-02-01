plugins {
    id("boot-library")
}

group = "org.example.driver"

dependencies {
    api("org.example.core:spring-core")
    api("org.springframework.boot:spring-boot-starter-data-redis")
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}