pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "kafka-pod"

includeBuild("../gradle-plugins")
includeBuild("../Core")
includeBuild("../kafka-pod")
includeBuild("../drivers")
