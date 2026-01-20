plugins{
    `java-library`
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
}

group = "org.example.web"

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
}

dependencies {
    api(project(":SpringPod"))
    api(project(":Core"))

    // Web and documentation
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.14")
}
