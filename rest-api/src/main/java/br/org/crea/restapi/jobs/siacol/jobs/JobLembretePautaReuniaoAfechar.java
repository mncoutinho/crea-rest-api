package br.org.crea.restapi.jobs.siacol.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.org.crea.commons.converter.commons.EmailEnvioConverter;
import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.dao.siacol.HabilidadePessoaDao;
import br.org.crea.commons.dao.siacol.PersonalidadeSiacolDao;
import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.dao.siacol.RlEmailReuniaoSiacolDao;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.EmailEnvio;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlEmailReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.siacol.converter.ReuniaoSiacolConverter;


/**
 * - Varre a base procurando por reuniões cuja falte 1 dia para expirar o prazo da pauta. 
 * - Job iniciado todos os dias a 08:50h.  
 * - Envia email com lembrete de criação de pauta para os interessados.
 * */
public class JobLembretePautaReuniaoAfechar implements Job {

	@Inject
	ReuniaoSiacolDao dao;

	@Inject HabilidadePessoaDao habilidadeDao;
	
	@Inject ReuniaoSiacolConverter reuniaoSiacolConverter;

	@Inject EmailDao emailDao;

	@Inject EmailService emailService;
	
	@Inject RlEmailReuniaoSiacolDao rlEmailReuniaoDao;
	
	@Inject PersonalidadeSiacolDao personalidadeDao;
	
	@Inject EmailService mailService;
	
	@Inject EmailEnvioConverter mailConverter;

	@Inject HttpClientGoApi httpGoApi;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		List<ReuniaoSiacol> listReuniao = new ArrayList<ReuniaoSiacol>();
		listReuniao = dao.getReunioesSemPautaCadastradaNoPeriodoLimite(context.getFireTime());

		listReuniao.forEach(reuniao -> enviaEmailCriacaoPauta(reuniao));
	}
	
	private void enviaEmailCriacaoPauta(ReuniaoSiacol reuniao) {
		
		ReuniaoSiacolDto reuniaoDto = new ReuniaoSiacolDto();
		reuniaoDto = reuniaoSiacolConverter.toDto(reuniao);
		
		ObjectMapper mapper = new ObjectMapper();
		EmailEnvioDto mailEnvio = new EmailEnvioDto();
				
		RlEmailReuniaoSiacol emailReuniao = new RlEmailReuniaoSiacol();
		DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
		destinatario.setNome(reuniao.getDepartamento().getNome());
		destinatario.setEmail(reuniao.getDepartamento().getEmailCoordenacao());
		List<DestinatarioEmailDto> listDestinatario = new ArrayList<DestinatarioEmailDto>();
		listDestinatario.add(destinatario);
		
		EmailEnvio emailEnvio = new EmailEnvio();
		emailEnvio.setAssunto("Lembrete criação Pauta: "+ reuniao.getDepartamento().getNome());

		emailEnvio.setEmissor("ctec@crea-rj.org.br");
		
		emailReuniao.setEmailEnvio(emailEnvio);
		emailReuniao.setReuniao(reuniao);
		
		mailEnvio = mailConverter.toDto(emailReuniao.getEmailEnvio());
		mailEnvio.setDataUltimoEnvio(new Date()); 
		mailEnvio.setDestinatarios(listDestinatario);
			
		String mensagem = "";
		mensagem += "<!DOCTYPE html>";
		mensagem += "<html>";
		mensagem += "<body>";
		mensagem += "</br><p>A reunião: " + reuniaoDto.getDataReuniaoFormatado() + " da " + reuniaoDto.getDepartamento().getNome() + " precisa de uma pauta</p>";
		mensagem += "</body>";
		mensagem += "</html>";
        
		mailEnvio.setMensagem(mensagem);

		mailService.envia(mailEnvio);
	}
}
