plugins {
    `java-library`
}

group = "org.example.user"
version = "1.0-SNAPSHOT"

dependencies {
    api(project(":MySqlDriver"))
}