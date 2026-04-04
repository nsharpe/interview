pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "media-metric-endpoint-root"

include(":media-metric-endpoint-webapp")
include(":media-metric-endpoint-sdk")
include(":media-metric-typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../util")
includeBuild("../spring-util")
includeBuild("../drivers")
includeBuild("../libs")
