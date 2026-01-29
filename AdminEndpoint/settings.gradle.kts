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
includeBuild("../SpringRest")
includeBuild("../MediaManagement")
includeBuild("../PublicRestEndpoint")
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
