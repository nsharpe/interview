plugins {
    id("java-convention")
}

group = "org.amoeba.example.data.structures"

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}