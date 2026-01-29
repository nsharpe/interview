pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "media-management-root"

include(":MediaManagementWebApp")
include(":MediaManagementSdk")

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
