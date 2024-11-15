plugins {
	kotlin("jvm")
	id("application")
	id("com.github.johnrengelman.shadow") version "7.0.0"
}

val clazzName = "dev.carloshenrique.locking_poc.redis.runner.MainKt"

application {
	mainClass.set(clazzName)
}

group = "dev.carloshenrique.locking_poc"

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation("org.redisson:redisson:3.16.8")
	runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.72.Final:osx-x86_64")
	implementation(project(":core"))
}

tasks {
	shadowJar {
		manifest {
			attributes(Pair("Main-Class", clazzName))
		}
	}
}