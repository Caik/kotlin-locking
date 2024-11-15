package dev.carloshenrique.locking_poc.redis

import dev.carloshenrique.locking_poc.core.Locking
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import java.util.concurrent.TimeUnit

object RedisLocking : Locking {

	private val client: RedissonClient

	init {
		val config = Config()
		val server = config.useSingleServer()
		server.address = "redis://${System.getenv("REDIS_HOST") ?: "localhost"}:6379"
		server.connectTimeout = 60000

		client = Redisson.create(config)
	}

	override fun tryLock(name: String): Boolean {
		return try {
			val lock = client.getLock(name)
			lock.tryLock(1, 4, TimeUnit.SECONDS)
		} catch (e: Exception) {
			false
		}
	}

	override fun unlock(name: String) {
		client.getLock(name).unlock()
	}

}