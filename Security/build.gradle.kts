plugins {
    `java-library`
}

group = "org.example.security"

tasks.bootJar{
    enabled=false
}

tasks.bootRun{
    enabled=false
}

dependencies {
    api(project(":SpringWeb"))
    api("org.springframework.boot:spring-boot-starter-security")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}