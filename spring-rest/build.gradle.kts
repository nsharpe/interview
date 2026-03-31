plugins{
    id("boot-library")
}

group = "org.amoeba.example.web"

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}