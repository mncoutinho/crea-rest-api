package br.org.crea.commons.cdi.factory;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

public class CDIJobFactory implements JobFactory {

	@Inject
	BeanManager beanManager;

	@Override
	public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
		JobDetail jobDetail = bundle.getJobDetail();
	    Class<? extends Job> jobClazz = jobDetail.getJobClass();
	    Bean<?> bean = beanManager.getBeans(jobClazz).iterator().next();
	    CreationalContext<?> ctx = beanManager.createCreationalContext(bean);
	    return (Job) beanManager.getReference(bean, jobClazz, ctx);
	}

}
