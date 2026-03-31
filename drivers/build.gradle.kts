plugins {
    id("java-convention")
}

group = "org.amoeba.example.drivers"

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}