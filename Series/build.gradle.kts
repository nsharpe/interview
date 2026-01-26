plugins {
    id("boot-library")
}

group = "org.example.series"

dependencies {
    api("org.example.core:spring-core")
    api(project(":MySqlDriver"))
}
