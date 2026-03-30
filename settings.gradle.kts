pluginManagement {
    includeBuild("gradle-plugins")
}

rootProject.name = "media-ecosystem"

includeBuild("gradle-plugins")
includeBuild("avro-model")
includeBuild("java-core")
includeBuild("drivers")
includeBuild("BusinessDomain")

// Pod setup
includeBuild("kafka-pod")
includeBuild("spring-pod")
includeBuild("spring-rest")

// Rest Deployable
includeBuild("admin-endpoint-root")
includeBuild("media-management-root")
includeBuild("user-management-root")
includeBuild("media-player-endpoint-root")
includeBuild("qa-endpoint-root")
includeBuild("media-metric-endpoint-root")

// UI
includeBuild("media-player-ui")

includeBuild("test-data")
include("integration-tests")
