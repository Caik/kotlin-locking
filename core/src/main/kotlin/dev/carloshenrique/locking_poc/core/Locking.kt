package dev.carloshenrique.locking_poc.core

interface Locking {
	fun tryLock(name: String): Boolean
	fun unlock(name: String)
}