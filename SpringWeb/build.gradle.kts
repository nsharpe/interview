plugins{
    `java-library`
}

group = "org.example.web"

dependencies {
    api(project(":SpringPod"))
    api(project(":Core"))

    // Web and documentation
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
}
