package dev.carloshenrique.locking_poc.database

import dev.carloshenrique.locking_poc.core.Locking
import dev.carloshenrique.locking_poc.database.dao.JobDAO
import java.util.*

object DatabaseLocking : Locking {

	private val uuid: String = UUID.randomUUID().toString()

	override fun tryLock(name: String): Boolean {
		return try {
			JobDAO.tryLock(uuid, name)
		} catch (e: Exception) {
			false
		}
	}

	override fun unlock(name: String) {
		JobDAO.unlock(uuid, name)
	}

}