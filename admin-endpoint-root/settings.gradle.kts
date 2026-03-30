pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "admin-endpoint-root"

include(":admin-endpoint-web-app")
include(":admin-sdk")
include(":admin-typescript-sdk")

project(":admin-endpoint-web-app").name = "admin-endpoint-web-app"
project(":admin-sdk").name = "admin-sdk"

includeBuild("../gradle-plugins")
includeBuild("../java-core")
includeBuild("../spring-rest")
includeBuild("../media-management-root")
includeBuild("../user-management-root")
includeBuild("../drivers")
includeBuild("../libs")
