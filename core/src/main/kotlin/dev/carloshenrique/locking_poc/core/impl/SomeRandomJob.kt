package dev.carloshenrique.locking_poc.core.impl

import dev.carloshenrique.locking_poc.core.Locking
import dev.carloshenrique.locking_poc.core.logging.Logger
import org.quartz.Job
import org.quartz.JobExecutionContext
import java.time.LocalDateTime

class SomeRandomJob : Job {

	override fun execute(context: JobExecutionContext?) {
		val jobKey = context?.jobDetail?.key?.name ?: return
		val locking = context.mergedJobDataMap["locking"]

		if (locking !is Locking)
			return

		val second = LocalDateTime.now().second.toString().padStart(2, '0')

		Logger.log("Trying to acquire lock", second)

		if (!locking.tryLock(jobKey)) {
			Logger.log("Couldn't acquire lock")
			Logger.flush()
			return
		}

		Logger.log("Lock acquired")

		// Doing the actual work
		doWork()

		Logger.log("Releasing lock")
		locking.unlock(jobKey)
		Logger.log("Lock released")

		Logger.flush()
	}

	private fun doWork() {
		Logger.log("Doing some work for 3s")
		Thread.sleep(3000)
		Logger.log("Work finished")
	}
}