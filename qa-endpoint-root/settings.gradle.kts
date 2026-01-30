pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "qa-endpoint-root"

include(":qa-endpoint-webapp")
include(":qa-endpoint-sdk")

includeBuild("../Plugins")
includeBuild("../Core")
includeBuild("../SpringRest")
includeBuild("../TestData")

