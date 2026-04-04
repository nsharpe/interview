pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "media-player-endpoint-root"

include(":media-player-endpoint-webapp")
include(":media-player-endpoint-sdk")
include(":media-player-typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../util")
includeBuild("../spring-util")
includeBuild("../drivers")
includeBuild("../libs")

