package dev.carloshenrique.locking_poc.hazelcast

import com.hazelcast.config.Config
import com.hazelcast.config.cp.FencedLockConfig
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import dev.carloshenrique.locking_poc.core.Locking
import java.util.concurrent.TimeUnit

object HazelcastLocking : Locking {

	private val instance: HazelcastInstance

	init {
		val config = Config()
		val cpSubsystemConfig = config.cpSubsystemConfig

		config.jetConfig.isEnabled = true
		cpSubsystemConfig.sessionHeartbeatIntervalSeconds = 1
		cpSubsystemConfig.sessionTimeToLiveSeconds = 8
		cpSubsystemConfig.addLockConfig(FencedLockConfig("some-random-job", 1))

		instance = Hazelcast.newHazelcastInstance(config)
	}

	override fun tryLock(name: String): Boolean {
		return try {
			val lock = instance.cpSubsystem.getLock(name)
			lock.tryLock(1, TimeUnit.SECONDS)
		} catch (e: Exception) {
			false
		}
	}

	override fun unlock(name: String) {
		instance.cpSubsystem.getLock(name).unlock()
	}

}