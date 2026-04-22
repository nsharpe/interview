plugins {
    id("boot-library")
}

group = "org.amoeba.example.spring.util"

dependencies {
    api(project(":security-flux"))
    api(project(":spring-pod"))
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.17")

    testImplementation("io.projectreactor:reactor-test")
}
