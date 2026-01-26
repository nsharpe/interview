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
includeBuild("BusinessDomain")
{
    dependencySubstitution {
        substitute(module("org.example.business-domain:series"))
            .using(project(":Series"))
    }
}
includeBuild("Driver"){
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
includeBuild("SpringWeb")

include("PublicRestEndpoint")
include("TestData")
include("IntegrationTests")
include("MediaManagement")
include("Users")
include("MediaManagementSdk")
include("PublicRestEndpointSdk")
include("MediaPlayerEndpoint")
include("MediaPlayerEndpointSdk")
include("KafkaPod")
