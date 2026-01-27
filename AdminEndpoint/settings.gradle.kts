pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "AdminEndpoint"

include("WebApp")

includeBuild("../Plugins")
includeBuild("../Core")
includeBuild("../SpringRest") {
    dependencySubstitution {
        substitute(module("org.example.web:spring-web"))
            .using(project(":SpringWeb"))
    }
}
includeBuild("../Driver") {
    dependencySubstitution {
        substitute(module("org.example.driver:mysql-driver"))
            .using(project(":MySql"))
        substitute(module("org.example.driver:postgres-driver"))
            .using(project(":Postgres"))
        substitute(module("org.example.driver:kafka-driver"))
            .using(project(":Kafka"))
        substitute(module("org.example.driver:redis-driver"))
            .using(project(":Redis"))
    }
}
includeBuild("../BusinessDomain")
{
    dependencySubstitution {
        substitute(module("org.example.business-domain:series"))
            .using(project(":Series"))
        substitute(module("org.example.business-domain:users"))
            .using(project(":Users"))
    }
}