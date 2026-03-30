pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="business-domain"

include("series")
include("users")

includeBuild("../java-core")
includeBuild("../gradle-plugins")
includeBuild("../drivers")