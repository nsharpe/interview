plugins {
    id("boot-library")
}

tasks.bootJar{
    enabled = false
}

tasks.bootRun{
    enabled = false
}

tasks.build {
    val otherBuild = gradle.includedBuild("apps")
    dependsOn(otherBuild.task(":build"))
}

group = "org.amoeba.example.test.data"

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

dependencies {

    api("org.amoeba.example.drivers:redis-driver")
    api("net.datafaker:datafaker:2.5.3")
    api("org.amoeba.example.media.management:sdk")
    api("org.amoeba.example.apps.user_management.sdk:sdk")
    api("org.amoeba.example.media.player:sdk")
    api("org.amoeba.example.admin:sdk")

    api("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.springframework.boot:spring-boot-docker-compose")
}