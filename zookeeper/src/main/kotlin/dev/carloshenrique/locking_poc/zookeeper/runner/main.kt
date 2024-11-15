package dev.carloshenrique.locking_poc.zookeeper.runner

import dev.carloshenrique.locking_poc.core.impl.JobScheduler
import dev.carloshenrique.locking_poc.zookeeper.ZookeeperLocking

fun main() {
	println("Starting app")

	// creating a new instance of a Locking mechanism using Zookeeper
	val locking = ZookeeperLocking

	// creating a new instance of the JobScheduler and passing the Zookeeper Locking mechanism
	JobScheduler(locking)
}
