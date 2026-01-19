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
    api(project(":Core"))
    api(project(":MySqlDriver"))
}
