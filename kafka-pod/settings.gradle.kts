pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name = "kafka-pod"

includeBuild("../Plugins")
includeBuild("../Core")
includeBuild("../kafka-pod")
includeBuild("../Driver")
