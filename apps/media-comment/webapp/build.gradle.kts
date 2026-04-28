plugins {
    id("web-convention")
}


group = "org.amoeba.example.app.media.comment"


tasks.bootBuildImage {
    imageName= "media-player/media-management:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

dependencies {
    implementation("org.amoeba.example.spring.util:webflux")
    implementation("org.amoeba.example.libs:comments")

     // SQL
    implementation("org.amoeba.example.drivers:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
}
