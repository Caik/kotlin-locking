plugins {
	kotlin("jvm")
	id("application")
	id("com.github.johnrengelman.shadow") version "7.0.0"
}

val clazzName = "dev.carloshenrique.locking_poc.database.runner.MainKt"

application {
	mainClass.set(clazzName)
}

group = "dev.carloshenrique.locking_poc"

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation("mysql:mysql-connector-java:8.0.27") {
		exclude("com.google.protobuf", "protobuf-java")
	}
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jre8:1.2.71")
	implementation(project(":core"))
}

tasks {
	shadowJar {
		manifest {
			attributes(Pair("Main-Class", clazzName))
		}
	}
}