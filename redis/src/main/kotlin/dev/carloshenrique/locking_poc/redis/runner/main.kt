package dev.carloshenrique.locking_poc.redis.runner

import dev.carloshenrique.locking_poc.core.impl.JobScheduler
import dev.carloshenrique.locking_poc.redis.RedisLocking

fun main() {
	println("Starting app")

	// creating a new instance of a Locking mechanism using Redis
	val locking = RedisLocking

	// creating a new instance of the JobScheduler and passing the Redis Locking mechanism
	JobScheduler(locking)
}

