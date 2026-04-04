pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "spring-rest"

include("security")
include("spring-web")

includeBuild("../spring-pod")
includeBuild("../util")
includeBuild("../gradle-plugins")
includeBuild("../drivers")