plugins {
    id("boot-library")
}

group = "org.example.user"

dependencies {
    api(project(":Core"))
    api(project(":PostgressqlDriver"))
    runtimeOnly("org.postgresql:postgresql")
}
