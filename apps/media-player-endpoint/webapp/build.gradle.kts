import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("web-convention")
}

group = "org.amoeba.example.media.player"

base {
    archivesName = "media-player-webapp"
}


configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

dependencies {
    implementation("org.amoeba.example.spring.util:spring-web")
    implementation("org.amoeba.example.avro:avro-model")
    implementation("org.amoeba.example.drivers:kafka-driver")
}
