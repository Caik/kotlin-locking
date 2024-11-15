package dev.carloshenrique.locking_poc.database.dao

import java.sql.Connection
import java.sql.DriverManager

object JobDAO {

	private val url: String
	private val user: String
	private val pass: String

	init {
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance()

		url = "jdbc:mysql://${System.getenv("DATABASE_HOST") ?: "localhost"}/${System.getenv("DATABASE_NAME") ?: "locking-poc"}"
		user = System.getenv("DATABASE_USER") ?: "random-user"
		pass = System.getenv("DATABASE_PASS") ?: "random-pass"
	}

	fun tryLock(uuid: String, name: String): Boolean {
		val updateSQL = """
			UPDATE 
				jobs
			SET
				holder_uuid = ?,
				available = 0,
				start_time = NOW()
			WHERE
				name = ? AND
				(
					available = 1 OR 
					(UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(start_time)) > 8
				)
		""".trimIndent()

		return try {
			// <- transaction begins
			getConnection().use {
				it.autoCommit = false

				val updateStmt = it.prepareStatement(updateSQL)
				updateStmt ?: return false

				updateStmt.setString(1, uuid)
				updateStmt.setString(2, name)

				val rowsAffected = updateStmt.executeUpdate()
				val ok = rowsAffected == 1

				if (ok)
					it.commit()
				else
					it.rollback()

				// <- transaction ends
				it.autoCommit = true

				ok
			}
		} catch (e: Exception) {
			false
		}
	}

	fun unlock(uuid: String, name: String) {
		val updateSQL = """
			UPDATE 
				jobs
			SET
				holder_uuid = NULL,
				available = 1
			WHERE
				holder_uuid = ? AND
				name = ?
		""".trimIndent()

		try {
			getConnection().use {
				val updateStmt = it.prepareStatement(updateSQL)

				updateStmt.setString(1, uuid)
				updateStmt.setString(2, name)

				updateStmt.executeUpdate()
			}
		} catch (_: Exception) {
		}
	}

	private fun getConnection(): Connection {
		return DriverManager.getConnection(url, user, pass)
	}
}