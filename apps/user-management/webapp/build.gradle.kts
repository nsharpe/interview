plugins {
    id("web-convention")
}

group = "org.amoeba.example.apps.user-management"

base {
    archivesName = "user-management-webapp"
}


tasks.bootBuildImage {
    imageName = "media-player/user-rest-endpoint:test"
}

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}


dependencies {
    implementation("org.amoeba.example.spring.util:webflux")
    implementation("org.amoeba.example.libs:users")

     // SQL
    implementation("org.amoeba.example.drivers:postgres-driver")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")

     // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}
