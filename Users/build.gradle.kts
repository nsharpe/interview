plugins {
    id("boot-library")
}

group = "org.example.user"

dependencies {
    api("org.example.core:spring-core")
    api("org.example.driver:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
}
