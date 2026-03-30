pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "media-management-root"

include(":media-management-webapp")
include(":media-management-sdk")
include("media-management-typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../java-core")
includeBuild("../spring-rest")
includeBuild("../drivers")
includeBuild("../BusinessDomain")