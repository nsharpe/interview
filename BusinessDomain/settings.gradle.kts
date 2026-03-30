pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="business-domain"

include("Series")
include("Users")

includeBuild("../Core")
includeBuild("../gradle-plugins")
includeBuild("../Driver")