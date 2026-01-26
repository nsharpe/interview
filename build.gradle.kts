plugins {
    id("java-convention")
    id("org.springframework.boot") version "3.5.10" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
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
        plugin("java-convention")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        maxHeapSize = "512m"
    }


}