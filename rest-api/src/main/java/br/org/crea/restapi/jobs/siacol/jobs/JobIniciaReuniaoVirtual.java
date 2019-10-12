package br.org.crea.restapi.jobs.siacol.jobs;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;


/**
 * Varre a base procurando por reuniões virtuais cuja data de abertura é correspondente ao dia. 
 * Pega somente reuniões que contenham pauta finalizada.
 * Job iniciado todos os dias a 00:00h. 
 * */
public class JobIniciaReuniaoVirtual implements Job {
	
	@Inject
	ReuniaoSiacolDao dao;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		dao.iniciaReuniaoVirtual(context.getFireTime());
		System.out.println(" >>>> >>> job inicio reuniao siacol rodando " );
	}

}
