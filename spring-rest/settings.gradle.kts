pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "spring-rest"

include("security")
include("spring-web")

includeBuild("../spring-pod")
includeBuild("../java-core")
includeBuild("../gradle-plugins")
includeBuild("../drivers")