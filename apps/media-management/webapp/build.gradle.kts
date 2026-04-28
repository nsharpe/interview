plugins {
    id("web-convention")
}

group = "org.amoeba.example.media.management"

base {
    archivesName = "webapp"
}

tasks.bootBuildImage {
    imageName= "media-player/media-management:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

dependencies {
    implementation("org.amoeba.example.spring.util:spring-web")
    implementation("org.amoeba.example.libs:series")

     // SQL
    implementation("org.amoeba.example.drivers:mysql-driver")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

     // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}
