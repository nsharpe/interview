pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="libs"

include("series")
include("users")
include("avro-model")

includeBuild("../util")
includeBuild("../gradle-plugins")
includeBuild("../drivers")