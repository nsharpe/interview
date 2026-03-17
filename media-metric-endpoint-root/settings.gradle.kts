pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "media-metric-endpoint-root"

include(":media-metric-endpoint-webapp")
include(":media-metric-endpoint-sdk")
include(":media-metric-typescript-sdk")

includeBuild("../Plugins")
includeBuild("../Core")
includeBuild("../SpringRest")
includeBuild("../Driver")
includeBuild("../BusinessDomain")
