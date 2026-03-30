pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "user-management-root"

include(":public-rest-endpoint-webapp")
include(":public-rest-endpoint-sdk")
include(":public-rest-endpoint-typescript-sdk")

project(":public-rest-endpoint-webapp").name = "public-rest-endpoint-webapp"
project(":public-rest-endpoint-sdk").name = "public-rest-endpoint-sdk"

includeBuild("../gradle-plugins")
includeBuild("../java-core")
includeBuild("../spring-rest")
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