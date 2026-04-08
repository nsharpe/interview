pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "apps"

include(":admin-endpoint")
include(":admin-endpoint:admin-endpoint-web-app")
include(":admin-endpoint:admin-sdk")
include(":admin-endpoint:admin-typescript-sdk")

include(":user-management-root")
include(":user-management-root:user-management-endpoint-webapp")
include(":user-management-root:user-management-endpoint-sdk")
include(":user-management-root:user-management-endpoint-typescript-sdk")

include(":media-management-root")
include(":media-management-root:media-management-webapp")
include(":media-management-root:media-management-sdk")
include(":media-management-root:media-management-typescript-sdk")

include(":media-metric-endpoint-root:media-metric-endpoint-webapp")
include(":media-metric-endpoint-root:media-metric-endpoint-sdk")
include(":media-metric-endpoint-root:media-metric-typescript-sdk")


include(":media-player-endpoint-root:media-player-endpoint-webapp")
include(":media-player-endpoint-root:media-player-endpoint-sdk")
include(":media-player-endpoint-root:media-player-typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../util")
includeBuild("../spring-util")
includeBuild("../drivers")
includeBuild("../libs")
