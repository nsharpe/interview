pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "user-management-root"

include(":user-management-endpoint-webapp")
include(":public-rest-endpoint-sdk")
include(":public-rest-endpoint-typescript-sdk")

project(":user-management-endpoint-webapp")
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