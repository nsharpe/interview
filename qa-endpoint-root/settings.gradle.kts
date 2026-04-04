pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "qa-endpoint-root"

include(":qa-endpoint-webapp")
include(":qa-endpoint-sdk")
include(":qa-endpoint-typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../util")
includeBuild("../spring-util")
includeBuild("../test-data")

