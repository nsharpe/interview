plugins {
    id("java-convention")
    id("org.springframework.boot") version "3.5.10" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("io.freefair.lombok") version "9.1.0"
}

allprojects {
    group = "org.example"
    version = "1.0-SNAPSHOT"
}

tasks.test {
    useJUnitPlatform()
    enabled = false
}

subprojects {
    apply{
        plugin("java-convention")
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

    tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
        systemProperty("spring.docker.compose.file",
            rootProject.file("docker-compose.yml").absolutePath +","+
                    rootProject.file("docker-compose.fixedport.yml").absolutePath)
    }
}