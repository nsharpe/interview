pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "admin-endpoint-root"

include(":admin-endpoint-web-app")
include(":admin-sdk")
include(":admin-typescript-sdk")

project(":admin-endpoint-web-app").name = "admin-endpoint-web-app"
project(":admin-sdk").name = "admin-sdk"

includeBuild("../Plugins")
includeBuild("../Core")
includeBuild("../SpringRest") {
    dependencySubstitution {
        substitute(module("org.example.web:spring-web"))
            .using(project(":SpringWeb"))
    }
}
includeBuild("../MediaManagement") {
    dependencySubstitution {
        substitute(module("org.example.media.management:media-management-sdk"))
            .using(project(":MediaManagementSdk"))
    }
}
includeBuild("../PublicRestEndpoint") {
    dependencySubstitution {
        substitute(module("org.example.public.rest:public-rest-sdk"))
            .using(project(":PublicRestEndpointSdk"))
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
