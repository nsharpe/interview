plugins {
    id("boot-library")
}

group = "org.example.user"

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

dependencies {
    api("org.example.core:spring-core")
    api("org.example.driver:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
}
