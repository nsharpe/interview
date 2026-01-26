plugins {
    id("boot-library")
}

group = "org.example.user"

dependencies {
    api("org.example.core:spring-core")
    api(project(":PostgressqlDriver"))
    runtimeOnly("org.postgresql:postgresql")
}
