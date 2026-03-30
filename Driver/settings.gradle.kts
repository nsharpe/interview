pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="Driver"

include("mysql-driver")
include("postgres-driver")
include("kafka-driver")
include("redis-driver")

includeBuild("../gradle-plugins")
includeBuild("../AvroModel")

