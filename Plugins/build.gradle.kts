plugins {
    `kotlin-dsl` // Essential for .gradle.kts plugins
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.5.10")
}

tasks.test{
    enabled=false
}