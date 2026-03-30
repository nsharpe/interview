pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "media-metric-endpoint-root"

include(":media-metric-endpoint-webapp")
include(":media-metric-endpoint-sdk")
include(":media-metric-typescript-sdk")

includeBuild("../gradle-plugins")
includeBuild("../java-core")
includeBuild("../spring-rest")
includeBuild("../drivers")
includeBuild("../libs")
