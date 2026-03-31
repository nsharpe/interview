plugins {
    id("boot-library")
}

group = "org.amoeba.example.libs"

configure<com.github.spotbugs.snom.SpotBugsExtension> {
    excludeFilter.set(file("${rootDir}/../spotbugs-exclude.xml"))
}

dependencies {
    api("org.amoeba.example.core:java-core")
    api("org.amoeba.example.drivers:mysql-driver")
}
