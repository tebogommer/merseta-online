package haj.com.jobs;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import haj.com.service.ScheduleService;

public class THUKJob implements Job {

	protected final Log logger = LogFactory.getLog(THUKJob.class);
	private ScheduleService scheduleService = new ScheduleService();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		logger.info("THUKJob: " + jobKey + " starting at " + new Date());
		scheduleService.runSchedule();
		logger.info("THUKJob: " + jobKey + " ended at " + new Date());
	}

}
