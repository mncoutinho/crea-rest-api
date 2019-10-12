package br.org.crea.restapi.jobs.siacol.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.org.crea.commons.converter.cadastro.domains.DocumentoConverter;
import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.converter.commons.EmailEnvioConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.siacol.PersonalidadeSiacolDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.dao.siacol.RlDocumentoProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.RlEmailReuniaoSiacolDao;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.StatusDocumento;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.siacol.EmailConselheiro;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlEmailReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.documento.PautaDto;
import br.org.crea.commons.service.DocumentoService;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.commons.FinalizarDocumentoPautaVirtual;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.corporativo.service.AuthService;

import com.google.gson.Gson;















/**
 * - Varre a base procurando por reuniões virtuais cuja prazo da pauta expirou para fechar
 * - Job iniciado todos os dias a 00:00h. Até o último minuto do dia que corresponde ao prazo a pauta estará aberta.
 * - No filtro das reuniões só serão buscadas pautas a fechar com status válido, ou seja, diferente de excluido, finalizado ou cancelado.
 * - Insere protocolos na pauta e envia o link contendo a pauta para os interessados por email.
 * */
public class JobFinalizaPautaReuniao implements Job {
	
	@Inject ReuniaoSiacolDao dao;
	
	@Inject ProtocoloSiacolDao protocoloSiacolDao;
	
	@Inject RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloDao;
	
	@Inject RlEmailReuniaoSiacolDao rlEmailReuniaoDao;
	
	@Inject DocumentoDao documentoDao;
	
	@Inject DocumentoService documentoService;
	
	@Inject DocumentoConverter documentoConverter;
	
	@Inject ArquivoConverter arquivoConverter;
	
	@Inject PersonalidadeSiacolDao personalidadeDao;
	
	@Inject EmailEnvioConverter mailConverter;
	
	@Inject HttpClientGoApi httpGoApi;
	
	@Inject EmailService mailService;
	
	@Inject Properties properties;
	
	@Inject AuthService authService;
	
	@Inject FinalizarDocumentoPautaVirtual finalizarDocumentoPautaVirtual;
		
	private String url;

	@PostConstruct
	public void before () {
	 this.url = properties.getProperty("apache.url");
	}

	@PreDestroy
	public void reset () {
		properties.clear();
	}

	private List<ReuniaoSiacol> listReunioes;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("JOB FINALIZAR PAUTA FUNCIONANDO");
		try {
			
			listReunioes = dao.buscaPautaReuniaoVirtualFechamento(context.getFireTime());
			listReunioes.forEach(reuniao -> {
				UserFrontDto userFrontDto = new UserFrontDto();
				userFrontDto.setIdPessoa(new Long(0));
				reuniao.setPauta(finalizarDocumentoPautaVirtual.atualizaDocumento(reuniao.getPauta(),dao.getReuniaoPor(reuniao.getPauta().getId())));
				reuniao.getPauta().setArquivo(documentoService.geraArquivoJob(reuniao.getPauta(),  userFrontDto));
				documentoDao.update(reuniao.getPauta());
				insereProtocolosParaEncerrarPauta(reuniao);
				enviaPautaEmailInteressados(reuniao);
				atualizaStatusPauta(reuniao.getPauta());
			});
			
		} catch (Throwable e) {
			httpGoApi.geraLog("JobFinalizaPautaReuniao || execute", StringUtil.convertObjectToJson(listReunioes), e);
		}
	}
	
	private void insereProtocolosParaEncerrarPauta(ReuniaoSiacol reuniao) {
		rlDocumentoProtocoloDao.cadastraProtocolosFechamentoPauta(dao.getProtocolosAPautar(pesquisaAtributosPauta(reuniao)), reuniao.getPauta());
	}
	
	private void atualizaStatusPauta(Documento pauta) {

		StatusDocumento statusPauta = new StatusDocumento();
		statusPauta.setId(new Long(8));
		pauta.setStatusDocumento(statusPauta);
		pauta.setDataAtualizacao(new Date());
		documentoDao.update(pauta);
		
	}
	
	private ReuniaoSiacolDto pesquisaAtributosPauta(ReuniaoSiacol reuniao) {
		ReuniaoSiacolDto dto = new ReuniaoSiacolDto();
		Gson gson = new Gson(); 
		
		try {
			
			DepartamentoDto departamentoReuniao = new DepartamentoDto();
			departamentoReuniao.setId(reuniao.getDepartamento().getId());
			
			dto.setDepartamento(departamentoReuniao);
			dto.setOrdenacaoPauta(gson.fromJson(reuniao.getPauta().getRascunho(), PautaDto.class).getOrdenacaoPauta());
			dto.setListDigitoExclusaoProtocolo(gson.fromJson(reuniao.getPauta().getRascunho(), PautaDto.class).getListDigitoExclusaoProtocolo());
			dto.setIncluiProtocoloDesfavoravel(gson.fromJson(reuniao.getPauta().getRascunho(), PautaDto.class).incluiProtocoloDesfavoravel());
			dto.setVirtual(reuniao.getVirtual());
			
		} catch (Throwable e) {
			httpGoApi.geraLog("JobFinalizaPautaReuniao || pesquisaAtributosPauta", StringUtil.convertObjectToJson(reuniao), e);
		}
		
		return dto;
	}
	
	private void enviaPautaEmailInteressados(ReuniaoSiacol reuniao) {
		List<EmailConselheiro> mailConselheiros = new ArrayList<EmailConselheiro>();
		EmailEnvioDto mailEnvio = new EmailEnvioDto();
				
		RlEmailReuniaoSiacol emailReuniao = rlEmailReuniaoDao.getEmailsPor(reuniao.getId(), new Long(2));
		if( emailReuniao != null ) {
			
			mailEnvio = mailConverter.toDto(emailReuniao.getEmailEnvio());
			mailConselheiros.addAll(personalidadeDao.buscaEmailConselheirosEnvioPautaPor(reuniao.getDepartamento().getCodigo()));
			
			for (EmailConselheiro conselheiro : mailConselheiros) {
				
				if (conselheiro != null) {
					DestinatarioEmailDto dto = new DestinatarioEmailDto();
					dto.setNome(conselheiro.getPessoa() != null ? conselheiro.getPessoa().getNome() : "");
					dto.setEmail(conselheiro.getDescricao());
					mailEnvio.setDestinatarios(new ArrayList<DestinatarioEmailDto>(mailEnvio.getDestinatarios()));
					mailEnvio.getDestinatarios().add(dto);
				}
			}
			
			/**
			 * FIXME: Demandado por Juan -> Temporariamente a pauta será disponibilizada com um get em cad-documento.
			 *        Depois excluir a url temporaria e usar a que busca o documento no file system
			 *        descomentando bloco dentro do if abaixo
			 */
			mailEnvio.setDataUltimoEnvio(new Date()); 
			if (reuniao.getPauta().temArquivo()) {
				String mensagem = mailEnvio.getMensagem();
				String linkDownloadPauta =  url + "arquivos/" + reuniao.getPauta().getArquivo().getUri();
				mensagem += "<!DOCTYPE html>";
				mensagem += "<html>";
				mensagem += "<body>";
				mensagem += "</br><p>Segue o link da Pauta Virtual</p>";
				mensagem += "<p><a href='"+linkDownloadPauta+"'>Pauta da "+reuniao.getDepartamento().getNome()+"</a></p>";
				mensagem += "<p> URL: "+linkDownloadPauta+"</p>";
				mensagem += "</body>";
				mensagem += "</html>";
                
				mailEnvio.setMensagem(mensagem);
			}else{
				mailEnvio.setMensagem("TESTE E-MAIL SEM ARQUIVO");
			}
			mailService.envia(mailEnvio);
		}
	}

}
