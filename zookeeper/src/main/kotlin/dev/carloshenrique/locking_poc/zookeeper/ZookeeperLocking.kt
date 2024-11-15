package dev.carloshenrique.locking_poc.zookeeper

import dev.carloshenrique.locking_poc.core.Locking
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.framework.recipes.locks.InterProcessMutex
import org.apache.curator.retry.RetryNTimes
import java.util.concurrent.TimeUnit

object ZookeeperLocking : Locking {

	private val lockMap = mutableMapOf<String, InterProcessMutex>()
	private val client: CuratorFramework

	init {
		val address = "${System.getenv("ZOOKEEPER_HOST") ?: "localhost"}:2181"
		val retryPolicy = RetryNTimes(3, 100)

		client = CuratorFrameworkFactory.newClient(address, retryPolicy)
		client.start()
	}

	override fun tryLock(name: String): Boolean {
		return try {
			val lock = InterProcessMutex(client, "/$name")
			val ok = lock.acquire(1, TimeUnit.SECONDS)

			if (ok)
				lockMap[name] = lock

			ok
		} catch (e: Exception) {
			false
		}
	}

	override fun unlock(name: String) {
		val lock = lockMap[name] ?: return

		lock.release()
		lockMap.remove(name)
	}

}