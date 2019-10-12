package br.org.crea.restapi.jobs.siacol.jobs;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.org.crea.siacol.dao.ConfigPessoaSiacolDao;

public class JobGerenciaHabilidades implements Job {

	@Inject
	private ConfigPessoaSiacolDao dao;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		dao.desativaHabilidades(null);
		dao.ativaHabilidades();
		System.out.println(" >>>> >>> job siacol rodando HABILIDADES " );
	}

}
