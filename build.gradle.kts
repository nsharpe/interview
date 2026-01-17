plugins {
    java
    id("org.springframework.boot") version "3.5.9" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("com.github.spotbugs") version "6.0.1"
    id("io.freefair.lombok") version "9.1.0"
}

allprojects {
    group = "org.example"
    version = "1.0-SNAPSHOT"
}

tasks.test {
    enabled = false
}

subprojects {
    apply{
        plugin("java")
        plugin( "com.github.spotbugs")
        plugin("io.freefair.lombok")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    configure<com.github.spotbugs.snom.SpotBugsExtension> {
        excludeFilter.set(file("${rootDir}/spotbugs-exclude.xml"))
    }

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        maxHeapSize = "512m"
    }
}