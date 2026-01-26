pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name="business-domain"

include("Series")
include("Users")

includeBuild("../Core")
includeBuild("../Plugins")
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