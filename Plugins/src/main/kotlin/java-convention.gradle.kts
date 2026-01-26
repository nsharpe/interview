plugins {
    java
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

//configure<com.github.spotbugs.snom.SpotBugsExtension> {
//    excludeFilter.set(file("${rootDir}/spotbugs-exclude.xml"))
//}

tasks.withType<Test> {
    maxHeapSize = "512m"
}
