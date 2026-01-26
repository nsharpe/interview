plugins {
    id("boot-library")
}

group = "org.example.series"

dependencies {
    api(project(":Core"))
    api(project(":MySqlDriver"))
}
