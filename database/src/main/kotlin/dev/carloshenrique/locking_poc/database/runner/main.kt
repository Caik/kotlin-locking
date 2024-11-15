package dev.carloshenrique.locking_poc.database.runner

import dev.carloshenrique.locking_poc.core.impl.JobScheduler
import dev.carloshenrique.locking_poc.database.DatabaseLocking

fun main() {
	println("Starting app")

	// creating a new instance of a Locking mechanism using Database
	val locking = DatabaseLocking

	// creating a new instance of the JobScheduler and passing the Database Locking mechanism
	JobScheduler(locking)
}
