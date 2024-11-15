package dev.carloshenrique.locking_poc.core.impl

import dev.carloshenrique.locking_poc.core.Locking
import org.quartz.*
import org.quartz.impl.StdSchedulerFactory


class JobScheduler(private val locking: Locking) {

	init {
		val sf: SchedulerFactory = StdSchedulerFactory()

		val job = JobBuilder.newJob(SomeRandomJob::class.java)
			.withIdentity("some-random-job")
			.usingJobData(JobDataMap().also { it["locking"] = locking })
			.build()

		val trigger = TriggerBuilder.newTrigger()
			.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
			.build()

		sf.scheduler.scheduleJob(job, trigger)
		sf.scheduler.start()
	}

}