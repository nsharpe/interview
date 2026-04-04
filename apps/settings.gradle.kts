pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "apps"

include(":admin-endpoint-root")
include(":admin-endpoint-root:admin-endpoint-web-app")
include(":admin-endpoint-root:admin-sdk")
include(":admin-endpoint-root:admin-typescript-sdk")


includeBuild("../gradle-plugins")
includeBuild("../util")
includeBuild("../spring-util")
includeBuild("../media-management-root")
includeBuild("../user-management-root")
includeBuild("../drivers")
includeBuild("../libs")
