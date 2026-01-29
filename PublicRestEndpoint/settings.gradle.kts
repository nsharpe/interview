pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "PublicRestEndpoint"

include(":public-rest-endpoint-webapp")
include(":public-rest-endpoint-sdk")
include(":public-rest-endpoint-typescript-sdk")

project(":public-rest-endpoint-webapp").name = "public-rest-endpoint-webapp"
project(":public-rest-endpoint-sdk").name = "public-rest-endpoint-sdk"

includeBuild("../Plugins")
includeBuild("../Core")
includeBuild("../SpringRest") {
    dependencySubstitution {
        substitute(module("org.example.web:spring-web"))
            .using(project(":SpringWeb"))
    }
}
includeBuild("../Driver")
includeBuild("../BusinessDomain")
{
    dependencySubstitution {
        substitute(module("org.example.business-domain:series"))
            .using(project(":Series"))
        substitute(module("org.example.business-domain:users"))
            .using(project(":Users"))
    }
}