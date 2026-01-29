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
includeBuild("Driver") {
    dependencySubstitution {
        substitute(module("org.example.driver:mysql-driver"))
            .using(project(":MySql"))
        substitute(module("org.example.driver:postgres-driver"))
            .using(project(":Postgres"))
        substitute(module("org.example.driver:kafka-driver"))
            .using(project(":Kafka"))
        substitute(module("org.example.driver:redis-driver"))
            .using(project(":Redis"))
    }
}
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

include("TestData")
include("IntegrationTests")
include("MediaPlayerEndpoint")
include("MediaPlayerEndpointSdk")
include("KafkaPod")
