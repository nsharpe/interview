pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "apps"

include(":admin-endpoint")
include(":admin-endpoint:webapp")
include(":admin-endpoint:sdk")
include(":admin-endpoint:typescript-sdk")

include(":user-management")
include(":user-management:webapp")
include(":user-management:sdk")
include(":user-management:typescript-sdk")

include(":media-management")
include(":media-management:webapp")
include(":media-management:sdk")
include(":media-management:typescript-sdk")

include(":media-metric-endpoint")
include(":media-metric-endpoint:webapp")
include(":media-metric-endpoint:sdk")
include(":media-metric-endpoint:typescript-sdk")

include(":media-player-endpoint")
include(":media-player-endpoint:webapp")
include(":media-player-endpoint:sdk")
include(":media-player-endpoint:typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../util")
includeBuild("../spring-util")
includeBuild("../drivers")
includeBuild("../libs")
