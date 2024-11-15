package dev.carloshenrique.locking_poc.hazelcast.runner

import dev.carloshenrique.locking_poc.core.impl.JobScheduler
import dev.carloshenrique.locking_poc.hazelcast.HazelcastLocking
import java.util.logging.Filter
import java.util.logging.LogManager

fun main() {
	println("Starting app")

	setUpLogger()

	// creating a new instance of a Locking mechanism using Hazelcast
	val locking = HazelcastLocking

	// creating a new instance of the JobScheduler and passing the Hazelcast Locking mechanism
	JobScheduler(locking)
}

private fun setUpLogger() {
	val rootLogger = LogManager.getLogManager().getLogger("")

	for (h in rootLogger.handlers) {
		h.filter = Filter {
			!it.loggerName.startsWith("com.hazelcast.cp.internal.session.RaftSessionService")
		}
	}
}
