description = "v1_17_R1_P1"

plugins {
    id("wolfyutils.spigot.nms")
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    paperweight.paperDevBundle("1.17.1-R0.1-SNAPSHOT")
}
