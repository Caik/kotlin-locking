plugins {
	kotlin("jvm")
}

group = "dev.carloshenrique.locking_poc"

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation("org.quartz-scheduler:quartz:2.3.2")
}