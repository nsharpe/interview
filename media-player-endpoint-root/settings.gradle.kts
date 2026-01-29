pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "media-player-endpoint-root"

include(":media-player-endpoint-webapp")
include(":media-player-endpoint-sdk")

includeBuild("../Plugins")
includeBuild("../Core")
includeBuild("../SpringRest")
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
