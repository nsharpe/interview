pluginManagement {
    includeBuild("gradle-plugins")
}

rootProject.name = "MediaPlayer"

includeBuild("gradle-plugins")
includeBuild("avro-model")
includeBuild("java-core")
includeBuild("drivers")
includeBuild("BusinessDomain")
{
    dependencySubstitution {
        substitute(module("org.example.business-domain:series"))
            .using(project(":Series"))
        substitute(module("org.example.business-domain:users"))
            .using(project(":Users"))
    }
}

// Pod setup
includeBuild("kafka-pod")
includeBuild("SpringPod")
includeBuild("spring-rest")

// Rest Deployable
includeBuild("admin-endpoint-root")
includeBuild("media-management-root")
includeBuild("PublicRestEndpoint")
includeBuild("media-player-endpoint-root")
includeBuild("qa-endpoint-root")
includeBuild("media-metric-endpoint-root")

// UI
includeBuild("media-player-ui")

includeBuild("test-data")
include("integration-tests")
