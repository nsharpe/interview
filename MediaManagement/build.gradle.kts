plugins {
    java
}

group = "org.example.media.management"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":Core"))
    implementation(project(":SpringPod"))

    implementation(project(":MySqlDriver"))
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")
}