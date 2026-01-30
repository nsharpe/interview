pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "qa-endpoint-root"

include(":qa-endpoint-webapp")
include(":qa-endpoint-sdk")
include(":qa-endpoint-typescript-sdk")

includeBuild("../Plugins")
includeBuild("../Core")
includeBuild("../SpringRest")
includeBuild("../TestData")

