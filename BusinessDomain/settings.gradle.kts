pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="business-domain"

include("Series")
include("Users")

includeBuild("../java-core")
includeBuild("../gradle-plugins")
includeBuild("../drivers")