plugins {
    id("java-convention")
}

group = "org.amoeba.example.util"

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}