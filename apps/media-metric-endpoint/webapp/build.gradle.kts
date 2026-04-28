plugins {
    id("web-convention")
}

group = "org.amoeba.example.media.metric"

base {
    archivesName = "media-metric-webapp"
}


tasks.bootBuildImage {
    imageName = "media-metric/media-metric-endpoint:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

dependencies {
    implementation("org.amoeba.example.spring.util:spring-web")

     // SQL
    implementation("org.amoeba.example.drivers:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

     // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}
