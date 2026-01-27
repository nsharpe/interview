plugins {
    id("boot-library")
}

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
}

group = "org.example.test.data"

dependencies {

    api("org.example.driver:redis-driver")
    api("net.datafaker:datafaker:2.5.3")
    api(project(":MediaManagementSdk"))
    api(project(":PublicRestEndpointSdk"))
    api(project(":MediaPlayerEndpointSdk"))
    api("org.example.admin:admin-sdk")

    api("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-docker-compose")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}