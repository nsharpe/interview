pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "spring-rest"

include("Security")
include("SpringWeb")

includeBuild("../SpringPod")
includeBuild("../Core")
includeBuild("../Plugins")
includeBuild("../BusinessDomain")
{
    dependencySubstitution {
        substitute(module("org.example.business-domain:series"))
            .using(project(":Series"))
    }
}
includeBuild("../Driver"){
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