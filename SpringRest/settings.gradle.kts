pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "spring-rest"

include("Security")
include("SpringWeb")

includeBuild("../SpringPod")
includeBuild("../Core")
includeBuild("../Plugins")
includeBuild("../Driver")