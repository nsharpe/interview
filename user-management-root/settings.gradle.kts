pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "user-management-root"

include(":user-management-endpoint-webapp")
include(":user-management-endpoint-sdk")
include(":user-management-endpoint-typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../java-core")
includeBuild("../spring-rest")
includeBuild("../drivers")
includeBuild("../BusinessDomain")
