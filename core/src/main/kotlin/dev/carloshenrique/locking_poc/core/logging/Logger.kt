package dev.carloshenrique.locking_poc.core.logging

import kotlin.concurrent.getOrSet

object Logger {

	private val logLines = ThreadLocal<Pair<String, MutableList<String>>>()

	fun log(msg: String, key: String? = null) {
		logLines.getOrSet {
			key ?: throw IllegalArgumentException()
			key to mutableListOf()
		}.second.add(msg)
	}

	fun flush() {
		val (key, messages) = logLines.get() ?: return
		println("$key: ${messages.joinToString(" -> ")}")
		logLines.remove()
	}

}