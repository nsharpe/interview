pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="spring-util"

include("security")
include("spring-web")
include("spring-pod")
include("webflux")

includeBuild("../util")
includeBuild("../gradle-plugins")
