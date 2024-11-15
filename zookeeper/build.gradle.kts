plugins {
	kotlin("jvm")
	id("application")
	id("com.github.johnrengelman.shadow") version "7.0.0"
}

val clazzName = "dev.carloshenrique.locking_poc.zookeeper.runner.MainKt"

application {
	mainClass.set(clazzName)
}

group = "dev.carloshenrique.locking_poc"

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation("org.apache.curator:curator-recipes:5.2.0")
	implementation(project(":core"))
}

tasks {
	shadowJar {
		manifest {
			attributes(Pair("Main-Class", clazzName))
		}
	}
}