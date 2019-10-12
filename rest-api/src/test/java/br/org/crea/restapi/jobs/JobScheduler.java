package br.org.crea.restapi.jobs;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobScheduler {
	
	public void schedule(Person person, String greetings, int hour, int minute) {

		try {
			
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();

			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("greetings", greetings);
			jobDataMap.put("person", person);

			JobDetail job = JobBuilder.newJob(GreetingsJob.class)
					.setJobData(jobDataMap)
					.withIdentity(greetings, person.getName()).build();

			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();


			sched.scheduleJob(job, trigger);

			sched.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
