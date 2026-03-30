pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "admin-endpoint-root"

include(":admin-endpoint-web-app")
include(":admin-sdk")
include(":admin-typescript-sdk")

project(":admin-endpoint-web-app").name = "admin-endpoint-web-app"
project(":admin-sdk").name = "admin-sdk"

includeBuild("../gradle-plugins")
includeBuild("../java-core")
includeBuild("../spring-rest")
includeBuild("../media-management-root")
includeBuild("../PublicRestEndpoint")
includeBuild("../drivers")
includeBuild("../BusinessDomain")
{
    dependencySubstitution {
        substitute(module("org.example.business-domain:series"))
            .using(project(":Series"))
        substitute(module("org.example.business-domain:users"))
            .using(project(":Users"))
    }
}
