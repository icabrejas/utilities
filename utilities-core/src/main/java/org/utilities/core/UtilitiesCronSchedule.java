package org.utilities.core;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class UtilitiesCronSchedule {

	public static void start(Class<? extends Job> jobClass, String cronExpression, String name, String group)
			throws SchedulerException {
		JobDetail job = jobDetail(jobClass, name, group);
		CronTrigger trigger = cronTrigger(cronExpression, name, group);
		start(job, trigger);
	}

	public static JobDetail jobDetail(Class<? extends Job> jobClass, String name, String group) {
		return JobBuilder.newJob(jobClass)
				.withIdentity(name, group)
				.build();
	}

	public static CronTrigger cronTrigger(String cronExpression, String name, String group) {
		return TriggerBuilder.newTrigger()
				.withIdentity(name, group)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
	}

	public static void start(JobDetail job, Trigger trigger) throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
	}

}
