pluginManagement {
    includeBuild("Plugins")

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "MediaPlayer"

includeBuild("Plugins")
includeBuild("AvroModel")
includeBuild("Core")
includeBuild("Driver")
includeBuild("SpringPod")
includeBuild("SpringRest") {
    dependencySubstitution {
        substitute(module("org.example.web:spring-web"))
            .using(project(":SpringWeb"))
    }
}
includeBuild("BusinessDomain")
{
    dependencySubstitution {
        substitute(module("org.example.business-domain:series"))
            .using(project(":Series"))
        substitute(module("org.example.business-domain:users"))
            .using(project(":Users"))
    }
}
includeBuild("AdminEndpoint")
includeBuild("MediaManagement") {
    dependencySubstitution {
        substitute(module("org.example.media.management:media-management-web"))
            .using(project(":MediaManagementWebApp"))
        substitute(module("org.example.media.management:media-management-sdk"))
            .using(project(":MediaManagementSdk"))
    }
}
includeBuild("PublicRestEndpoint")
includeBuild("media-player-endpoint-root")

include("TestData")
include("IntegrationTests")
include("MediaPlayerEndpoint")
include("MediaPlayerEndpointSdk")
include("KafkaPod")

