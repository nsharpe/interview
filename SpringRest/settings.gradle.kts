pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "spring-rest"

include("Security")
include("spring-web")

includeBuild("../SpringPod")
includeBuild("../java-core")
includeBuild("../gradle-plugins")
includeBuild("../drivers")