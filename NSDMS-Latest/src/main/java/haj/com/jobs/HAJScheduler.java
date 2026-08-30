package haj.com.jobs;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

public class HAJScheduler {

	public void run() throws Exception {
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

		Scheduler sched = schedFact.getScheduler();

		sched.start();

		// define the job and tie it to our HelloJob class
		JobDetail job = newJob(THUKJob.class).withIdentity("THUKJob", "group1").build();

		Trigger trigger = newTrigger().withIdentity("THUKJobtrigger1", "group1")
				.withSchedule(org.quartz.CronScheduleBuilder.dailyAtHourAndMinute(00, 30)).forJob(job).build();

		/*
		 * Trigger trigger = newTrigger(). withIdentity("THUKJobtrigger1",
		 * "group1"). withSchedule( simpleSchedule(). withIntervalInMinutes(1).
		 * repeatForever() ).build();
		 */
		sched.scheduleJob(job, trigger);
	}

}
