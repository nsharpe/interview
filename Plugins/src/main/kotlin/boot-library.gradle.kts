
plugins {
    `java-library`
    id("java-convention")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
}

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:3.5.10"))
}