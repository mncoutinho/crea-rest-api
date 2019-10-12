package br.org.crea.restapi.jobs.siacol;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.org.crea.commons.cdi.factory.CDIJobFactory;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.restapi.jobs.siacol.jobs.JobFinalizaPautaReuniao;
import br.org.crea.restapi.jobs.siacol.jobs.JobFinalizaReuniaoVirtual;
import br.org.crea.restapi.jobs.siacol.jobs.JobGerenciaHabilidades;
import br.org.crea.restapi.jobs.siacol.jobs.JobIniciaReuniaoVirtual;
import br.org.crea.restapi.jobs.siacol.jobs.JobLembretePautaReuniaoAfechar;

@Startup
@Singleton
public class InitialJobsSiacol {
	
	@Inject
	private CDIJobFactory jobFactory;

	private Scheduler scheduler;
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	
	@PostConstruct
	public void postConstruct() throws SchedulerException {
		
//		taskLembretePautaReuniao();
		taskGerenciaHabilidades();
//		taskFinalizaPautaReuniaoVirtual();
//		taskIniciaReuniaoVirtual();
//		taskFinalizaReuniaoVirtual();
	}

	@PreDestroy
	public void preDestroy() throws SchedulerException {
		if (scheduler != null && scheduler.isStarted()) {
			scheduler.shutdown(false);
		}
	}
	
	public void taskGerenciaHabilidades() throws SchedulerException {
		
		JobDetail jobDetail = null;
		CronTrigger trigger = null;
		scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.setJobFactory(jobFactory);

		jobDetail = JobBuilder.newJob(JobGerenciaHabilidades.class).build();
//		trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 */50 * ? * *")).build();
		//trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 0 7am ? * MON-SUN")).build();
		trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 0 15pm ? * MON-SUN")).build();
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}
	
	public void taskFinalizaPautaReuniaoVirtual() throws SchedulerException {
		
		JobDetail jobDetail = null;
		CronTrigger trigger = null;
		scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.setJobFactory(jobFactory);

		jobDetail = JobBuilder.newJob(JobFinalizaPautaReuniao.class).build();
		//trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 ? * *")).build();
		
		//FIXME usar esta expressao aqui para testes
		trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 */10 * ? * *")).build();
		//trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 18 22 ? * *")).build();
		
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}
	
	public void taskIniciaReuniaoVirtual() throws SchedulerException {
		
		JobDetail jobDetail = null;
		CronTrigger trigger = null;
		scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.setJobFactory(jobFactory);

		jobDetail = JobBuilder.newJob(JobIniciaReuniaoVirtual.class).build();
		//trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 ? * *")).build();
		
		//FIXME usar esta expressao aqui para testes
		trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 */10 * ? * *")).build();
		//trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 43 23 ? * *")).build();
		
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}
	
	public void taskLembretePautaReuniao() {
		
		try {
			
			JobDetail jobDetail = null;
			CronTrigger trigger = null;
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.setJobFactory(jobFactory);
			
			jobDetail = JobBuilder.newJob(JobLembretePautaReuniaoAfechar.class).build();
			//trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 50 08 ? * *")).build();
			
			//FIXME usar esta expressao aqui para testes
			trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 */10 * ? * *")).build();
			//trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 46 00 ? * *")).build();
			
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			
		} catch (Exception e) {
			httpGoApi.geraLog("InitialJobsSiacol || taskLembretePautaReuniaoVirtual", StringUtil.convertObjectToJson("sem parametro"), e);
		}
		
	}
	
	public void taskFinalizaReuniaoVirtual() throws SchedulerException {
		
		JobDetail jobDetail = null;
		CronTrigger trigger = null;
		scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.setJobFactory(jobFactory);
		
		jobDetail = JobBuilder.newJob(JobFinalizaReuniaoVirtual.class).build();
		//trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("59 59 23 ? * *")).build();
		
		//FIXME usar esta expressao aqui para testes
		trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 */10 * ? * *")).build();
		//trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 55 01 ? * *")).build();
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}


}
