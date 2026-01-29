pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name="business-domain"

include("Series")
include("Users")

includeBuild("../Core")
includeBuild("../Plugins")
includeBuild("../Driver")