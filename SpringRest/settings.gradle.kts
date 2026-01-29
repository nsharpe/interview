pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "spring-rest"

include("Security")
include("spring-web")

includeBuild("../SpringPod")
includeBuild("../Core")
includeBuild("../Plugins")
includeBuild("../Driver")