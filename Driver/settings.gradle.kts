pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name="driver"

includeBuild("../Plugins")
includeBuild("../AvroModel")
include("MySql")
include("Postgres")
include("Kafka")