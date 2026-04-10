pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "apps"

include(":admin-endpoint")
include(":admin-endpoint:webapp")
include(":admin-endpoint:sdk")
include(":admin-endpoint:admin-typescript-sdk")

include(":user-management")
include(":user-management:webapp")
include(":user-management:sdk")
include(":user-management:user-management-endpoint-typescript-sdk")

include(":media-management")
include(":media-management:webapp")
include(":media-management:sdk")
include(":media-management:media-management-typescript-sdk")

include(":media-metric-endpoint:webapp")
include(":media-metric-endpoint:sdk")
include(":media-metric-endpoint:media-metric-typescript-sdk")


include(":media-player-endpoint:webapp")
include(":media-player-endpoint:sdk")
include(":media-player-endpoint:media-player-typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../util")
includeBuild("../spring-util")
includeBuild("../drivers")
includeBuild("../libs")
