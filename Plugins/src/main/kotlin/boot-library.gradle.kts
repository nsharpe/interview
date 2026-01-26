
plugins {
    `java-library`
    id("boot-convention")
}

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
}
