pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="libs"

include("series")
include("users")

includeBuild("../java-core")
includeBuild("../gradle-plugins")
includeBuild("../drivers")