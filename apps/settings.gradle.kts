pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "apps"

include(":admin-endpoint-root")
include(":admin-endpoint-root:admin-endpoint-web-app")
include(":admin-endpoint-root:admin-sdk")
include(":admin-endpoint-root:admin-typescript-sdk")

include(":user-management-root")
include(":user-management-root:user-management-endpoint-webapp")
include(":user-management-root:user-management-endpoint-sdk")
include(":user-management-root:user-management-endpoint-typescript-sdk")

include(":media-management-root")
include(":media-management-root:media-management-webapp")
include(":media-management-root:media-management-sdk")
include(":media-management-root:media-management-typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../util")
includeBuild("../spring-util")
includeBuild("../drivers")
includeBuild("../libs")
