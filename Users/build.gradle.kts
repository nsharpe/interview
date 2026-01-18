plugins {
    `java-library`
}

group = "org.example.user"

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
}

dependencies {
    implementation(project(":Core"))
    api(project(":MySqlDriver"))
}