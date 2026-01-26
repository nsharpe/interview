plugins {
    java
    id("com.github.spotbugs")
    id("io.freefair.lombok")
}

configure<JavaPluginExtension> {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    add("testImplementation", platform("org.junit:junit-bom:5.10.2"))
    add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
}


//configure<com.github.spotbugs.snom.SpotBugsExtension> {
//    excludeFilter.set(file("${rootDir}/spotbugs-exclude.xml"))
//}

tasks.withType<Test> {
    useJUnitPlatform()
    maxHeapSize = "512m"
}
