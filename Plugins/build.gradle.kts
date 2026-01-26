plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.5.10")
    implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:6.0.1")
}

tasks.test{
    enabled=false
}