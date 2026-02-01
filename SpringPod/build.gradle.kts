plugins {
    id("boot-library")
}

group = "org.example.pod"

val projectJarPaths = configurations.implementation.map { config ->
    config.allDependencies
        .withType<ProjectDependency>()
        .map { "${it.path}:jar" }
}

tasks.jar{
    dependsOn(projectJarPaths)
}

dependencies {
    api("org.example.core:spring-core")
    api( "org.springframework.boot:spring-boot-starter-web")
    api( "org.springframework.boot:spring-boot-starter-actuator")
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}