pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "spring-rest"

include("Security")
include("spring-web")

includeBuild("../SpringPod")
includeBuild("../Core")
includeBuild("../gradle-plugins")
includeBuild("../Driver")