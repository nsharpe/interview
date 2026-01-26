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
includeBuild("Driver"){
    dependencySubstitution {
        substitute(module("org.example.driver:mysql-driver"))
            .using(project(":MySql"))
        substitute(module("org.example.driver:postgres-driver"))
            .using(project(":Postgres"))
        substitute(module("org.example.driver:kafka-driver"))
            .using(project(":Kafka"))
    }
}

include("PublicRestEndpoint")
include("TestData")
include("IntegrationTests")
include("MediaManagement")
include("Users")
include("SpringPod")
include("SpringWeb")
include("Series")
include("MediaManagementSdk")
include("PublicRestEndpointSdk")
include("MediaPlayerEndpoint")
include("Security")
include("Redis")
include("MediaPlayerEndpointSdk")
include("KafkaPod")
