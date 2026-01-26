plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-dependencies:3.5.10")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.5.10")
    implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:6.0.1")
    implementation("io.freefair.lombok:io.freefair.lombok.gradle.plugin:9.2.0")
    implementation("org.openapi.generator:org.openapi.generator.gradle.plugin:7.19.0")
    implementation("org.springdoc.openapi-gradle-plugin:org.springdoc.openapi-gradle-plugin.gradle.plugin:1.9.0")
}

tasks.test{
    enabled=false
}