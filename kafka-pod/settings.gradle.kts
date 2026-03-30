pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "kafka-pod"

includeBuild("../gradle-plugins")
includeBuild("../java-core")
includeBuild("../kafka-pod")
includeBuild("../drivers")
