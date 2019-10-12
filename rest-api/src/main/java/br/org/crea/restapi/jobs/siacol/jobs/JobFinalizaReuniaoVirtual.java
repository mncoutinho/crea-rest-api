package br.org.crea.restapi.jobs.siacol.jobs;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.org.crea.siacol.service.ReuniaoSiacolService;


/**
 * - Varre a base procurando por reuniões virtuais que estão aptas a encerrar (último minuto do dia da reunião)
 * - Job iniciado todos os dias a 23:59: 59h. Até o último segundo do dia que corresponde a data da reunião, ela estará aberta para votação.
 * - Atualiza o status da reunião para 'FECHADA'
 * - Atualiza status dos protocolos da reunião finalizada.
 * */
public class JobFinalizaReuniaoVirtual implements Job {

	@Inject
	ReuniaoSiacolService service;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println(" >>>> >>> job finalizacao reuniao virtual siacol rodando " );
		service.finalizaReuniaoVirtual(context.getFireTime());
		
	}

}
