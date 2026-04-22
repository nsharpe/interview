
plugins {
    id("boot-library")
}

group = "org.amoeba.example.spring.util"

dependencies {
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.amoeba.example.drivers:redis-driver")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation("io.projectreactor:reactor-test")
}