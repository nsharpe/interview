plugins {
    `java-library`
}

group = "org.example.user"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":Core"))
    api(project(":MySqlDriver"))
}