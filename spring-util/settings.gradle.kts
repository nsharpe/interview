pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="spring-util"

include("security")
include("spring-web")

includeBuild("../util")
includeBuild("../gradle-plugins")
