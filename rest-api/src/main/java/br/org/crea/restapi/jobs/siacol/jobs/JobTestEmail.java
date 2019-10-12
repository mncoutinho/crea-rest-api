package br.org.crea.restapi.jobs.siacol.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class JobTestEmail implements Job {

	@Inject
	EmailDao emailDao;

	@Inject
	EmailService emailService;

	@Inject
	HttpClientGoApi httpGoApi;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

	    System.out.println(">> >> >> rodando");
//		enviaEmail("ricardo.goncalves@crea-rj.org.br", getEmailsPessoas());

	}

	private void enviaEmail(String emailRemetente, List<DestinatarioEmailDto> emailsDestinatarios) {

		EmailEnvioDto email = new EmailEnvioDto();

		try {

			email.setAssunto("Lemprete Pauta fechada");
			email.setEmissor(emailRemetente);
			email.setMensagem(">>>");
			email.setDataUltimoEnvio(new Date());
			email.setDestinatarios(emailsDestinatarios);

			emailService.envia(email);

		} catch (Exception e) {
			httpGoApi.geraLog("JobLembretePautaReuniaoAfechar || enviaEmail", StringUtil.convertObjectToJson(email), e);
		}

	}

	private List<DestinatarioEmailDto> getEmailsPessoas() {

		List<DestinatarioEmailDto> listDestanatarios = new ArrayList<DestinatarioEmailDto>();
		DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
		destinatario.setNome("teste");
		destinatario.setEmail("ricanalista@gmail.com");
	
        listDestanatarios.add(destinatario);
	

		return listDestanatarios;
	}

}
