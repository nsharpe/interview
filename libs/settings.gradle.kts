pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="libs"

include("series")
include("users")

includeBuild("../util")
includeBuild("../gradle-plugins")
includeBuild("../drivers")