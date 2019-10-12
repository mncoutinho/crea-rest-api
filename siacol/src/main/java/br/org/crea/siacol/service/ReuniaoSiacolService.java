package br.org.crea.siacol.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.google.gson.Gson;

import br.org.crea.commons.converter.cadastro.domains.DocumentoConverter;
import br.org.crea.commons.converter.commons.EmailEnvioConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.NumeroDocumentoDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.dao.siacol.PersonalidadeSiacolDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.RelatorioReuniaoSiacolDao;
import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.dao.siacol.RlDocumentoProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.RlReuniaoRelatorioSiacolDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolProtocoloFactory;
import br.org.crea.commons.factory.siacol.AuditaSiacolReuniaoFactory;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.NumeroDocumento;
import br.org.crea.commons.models.cadastro.StatusDocumento;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.EmailEnvio;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.StatusDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.siacol.EmailConselheiro;
import br.org.crea.commons.models.siacol.PresencaReuniaoSiacol;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.RlEmailReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlReuniaoRelatorioSiacol;
import br.org.crea.commons.models.siacol.StatusItemPauta;
import br.org.crea.commons.models.siacol.dtos.IndicadoresReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaVotoReuniaoDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.dtos.RelatorioReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.TesteSiacolGenericDto;
import br.org.crea.commons.models.siacol.dtos.documento.RascunhoDto;
import br.org.crea.commons.models.siacol.enuns.ClassificacaoProtocoloPautaEnum;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.models.siacol.enuns.StatusReuniaoSiacol;
import br.org.crea.commons.models.siacol.enuns.TipoRelatorioSiacolEnum;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.HttpFirebaseApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.EmailUtil;
import br.org.crea.commons.util.ItextUtil;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.siacol.converter.ProtocoloSiacolConverter;
import br.org.crea.siacol.converter.ReuniaoSiacolConverter;
import br.org.crea.siacol.dao.PresencaReuniaoSiacolDao;
import br.org.crea.siacol.relatorio.GeraConteudo;

public class ReuniaoSiacolService {

	@Inject
	private ReuniaoSiacolConverter converter;

	@Inject
	private ProtocoloSiacolConverter protocoloConverter;

	@Inject
	private ReuniaoSiacolDao dao;

	@Inject
	private DocumentoConverter documentoConverter;

	@Inject
	private RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloDao;

	@Inject
	private ProtocoloSiacolDao protocoloDao;

	@Inject
	private VotoReuniaoSiacolService votoService;

	@Inject
	private EmailService emailService;

	@Inject
	private DocumentoDao documentoDao;

	@Inject
	private HttpFirebaseApi firebaseApi;

	@Inject
	private HttpClientGoApi httpGoApi;

	@Inject
	private PresencaReuniaoSiacolDao presencaReuniaoDao;

	@Inject
	private PersonalidadeSiacolDao personalidadeDao;
	
	@Inject
	private RelatorioReuniaoSiacolDao relatorioDao;
		
	@Inject
	private RlReuniaoRelatorioSiacolDao rlReuniaoRelatorioDao;
	
	@Inject
	private GeraConteudo geraConteudo;
	
	@Inject 
	private ProfissionalDao profissionalDao;
	
	@Inject 
	private RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloSiacolDao;
	
	@Inject
	private EmailService mailService;
	
	@Inject 
	private EmailEnvioConverter mailConverter;
	
	@Inject 
	private AuditaSiacolReuniaoFactory auditaReuniaoFactory;
	
	@Inject 
	private AcaoReuniaoSiacolService acaoReuniaoService;
	
	@Inject 
	private NumeroDocumentoDao numeroDocumentoDao;
	
	@Inject 
	private AuditaSiacolProtocoloFactory audita;
	
	@Inject
	private EmailUtil emailUtil;
	
	public ReuniaoSiacolDto salvar(ReuniaoSiacolDto dto) {
		if (dao.existeReuniaoNestaData(dto)) {
			return null;
		}else {
			ReuniaoSiacol reuniao = converter.toModel(dto);
			return converter.toDto(dao.create(reuniao));
		}		
	}

	// FIXME pq um delete pauta neste método que atualiza o objeto reunião?

	public ReuniaoSiacolDto atualizar(ReuniaoSiacolDto dto) {
		
		if (dao.existeReuniaoNestaData(dto)) {
			return null;
		}else {		
			ReuniaoSiacol reuniao = converter.toModel(dto);
			
			// TROCAR A DATA DA HOMOLOGAÇÂO DOS PROFISSIONAIS!
			List<Long> listRegistroProfissional = null;
			if (dto.getPauta() != null && dto.getPauta().getStatus() != null && dto.getPauta().getStatus().getId() == 8) {		
				try {
					Gson gson = new Gson();
					RascunhoDto rascunhoDto = gson.fromJson(reuniao.getPauta().getRascunho(), RascunhoDto.class);
					listRegistroProfissional = rascunhoDto.getListRegistroProfissional();
				} catch (Exception e) {
					listRegistroProfissional = null;
				}
				GenericDto trocarDataHomologacaoDto = new GenericDto();
				trocarDataHomologacaoDto.setListIds(listRegistroProfissional);
				trocarDataHomologacaoDto.setDataInicio(dto.getDataReuniao());
				trocarDataHomologacaoDto.setDataFim(reuniao.getDataReuniao());
				if (listRegistroProfissional != null) {
					if (dto.isManterPauta()) {
						//profissionalDao.trocarDataHomologacaoProfissional(trocarDataHomologacaoDto);
					}else {
						profissionalDao.excluirHomologacaoProfissional(trocarDataHomologacaoDto);
					}
				}
			}
		
			dao.deletePauta(dto);
			return converter.toDto(dao.update(reuniao));
		}
	}

	public List<ReuniaoSiacolDto> getReunioesSiacol(PesquisaGenericDto dto) {
		return converter.toListDto(dao.getReunioesSiacol(dto));
	}

	public List<ProtocoloSiacolDto> getProtocolosAPautarEmReuniao(ReuniaoSiacolDto dto) {
		return protocoloConverter.toListDto(dao.getProtocolosAPautar(dto));
	}

	public List<ProtocoloSiacolDto> getProtocolosSemClassificacaoPautaPor(Long idDepartamento) {
		return protocoloConverter.toListDto(dao.getProtocolosSemClassificacaoPautaPor(idDepartamento));
	}

	public ProtocoloSiacolDto buscaProtocoloAReclassificarPor(Long numeroProtocolo, Long idDepartamento) {

		return protocoloConverter.toDto(dao.buscaProtocoloAReclassificarPor(numeroProtocolo, idDepartamento));
	}

	public DocumentoGenericDto getPautaPorIdReuniao(Long idReuniao) {

		ReuniaoSiacol reuniao = new ReuniaoSiacol();
		reuniao = dao.getBy(idReuniao);

		return reuniao.temPauta() ? documentoConverter.toDto(documentoDao.getBy(reuniao.getPauta().getId())) : null;

	}

	public List<ProtocoloSiacolDto> getProtocolosPautados(Long idPauta) {
		return protocoloConverter.toListDto(rlDocumentoProtocoloDao.getListProtocolos(idPauta));
	}

	public List<ProtocoloSiacolDto> getProtocolosAAssinar(Long idDepartamento) {
		return protocoloConverter.toListDto(dao.getProtocolosAAssinar(idDepartamento));
	}
	
	public List<ProtocoloSiacolDto> getProtocolosAAssinarAdReferendum(Long idDepartamento) {
		return protocoloConverter.toListDto(dao.getProtocolosAAssinarAdReferendum(idDepartamento));
	}

	public ReuniaoSiacolDto getReuniaoPor(Long idPauta) {
		return converter.toDto(dao.getReuniaoPor(idPauta));
	}
	
	public ReuniaoSiacolDto getReuniaoAnteriorPor(ReuniaoSiacolDto reuniaoAtual) {
		return converter.toDto(dao.getReuniaoAnteriorPor(reuniaoAtual));
	}

	public void finalizaReuniaoVirtual(Date dataVerificacaoFechamento) {

		for (ReuniaoSiacol reuniaoSiacol : dao.getReunioesAptasEncerrar(dataVerificacaoFechamento)) {

			reuniaoSiacol.setStatus(StatusReuniaoSiacol.FECHADA);
			reuniaoSiacol.setHoraFim(dataVerificacaoFechamento);
			dao.update(reuniaoSiacol);
			atualizaStatusProtocolosDeReuniaoFinalizada(reuniaoSiacol);
			atualizaItemReuniao(reuniaoSiacol);
		}

	}

	private void gerarNumeroDeDecisao(ProtocoloSiacol protocolo) {
		
		try {
			String numeroReal = "";
					
			NumeroDocumento numeroDocumento = numeroDocumentoDao.getNumeroDocumento(protocolo.getDepartamento().getId(), new Long(1102));
			
			if (numeroDocumento != null) {
				if (numeroDocumento.isTem_ano()) {
					numeroReal = numeroDocumento.getNumero() + "/" + numeroDocumento.getAno() + " - " + numeroDocumento.getDepartamento().getSigla(); 
				} else {
					numeroReal = numeroDocumento.getNumero() + " - " + numeroDocumento.getDepartamento().getSigla();
				}
				
				numeroDocumento.setNumero(numeroDocumento.getNumero()+1);
				numeroDocumentoDao.update(numeroDocumento);
			}
			
			RlDocumentoProtocoloSiacol item = rlDocumentoProtocoloDao.getItemByIdProtocolo(protocolo.getId());
			
			item.setNumeroDocumento(numeroReal);			
			rlDocumentoProtocoloDao.update(item);
			
			protocolo.setNumeroDecisao(numeroReal);
			protocoloDao.update(protocolo);
			
			atualizaNumeroDecisaoNoDocumento(numeroReal, protocolo.getNumeroProtocolo());
		} catch (Exception e) {
			httpGoApi.geraLog("ReuniaoSiacolService || gerarNumeroDeDecisao", StringUtil.convertObjectToJson(protocolo), e);
		}
		
	}

	private void atualizaNumeroDecisaoNoDocumento(String numeroReal, Long numeroProtocolo) {
		List<Documento> documentos = documentoDao.getDocumentosPorTipoENumeroProtocolo(new Long(1102), numeroProtocolo);
		
		if (!documentos.isEmpty()) {
			for (Documento documento : documentos) {
				documento.setNumeroDocumento(numeroReal);
				documentoDao.update(documento);
			}
		}		
	}
	
	private void atualizaItemReuniao(ReuniaoSiacol reuniaoSiacol) {
		StatusItemPauta status = new StatusItemPauta();
		status.setId(new Long(1));
		for (RlDocumentoProtocoloSiacol item : rlDocumentoProtocoloSiacolDao.getItensByIdDocumento(reuniaoSiacol.getPauta().getId())) {
			item.setStatus(status);
			rlDocumentoProtocoloSiacolDao.update(item);
		}
		
	}

	public void atualizaStatusProtocolosDeReuniaoFinalizada(ReuniaoSiacol reuniaoSiacol) {
		ProtocoloSiacol protocoloVotado = null;

		PesquisaVotoReuniaoDto pesquisaResultado = new PesquisaVotoReuniaoDto();
		pesquisaResultado.setIdPauta(reuniaoSiacol.getPauta().getId());			
		pesquisaResultado.setIdReuniao(reuniaoSiacol.getId());
		
		boolean efeitoLegal = false;
		
		try {
			Gson gson = new Gson();
			RascunhoDto rascunhoDto = gson.fromJson(reuniaoSiacol.getPauta().getRascunho(), RascunhoDto.class);
			efeitoLegal = rascunhoDto.isEfeitoLegal();
		} catch (Exception e) {
			efeitoLegal = false;
		}
		
		for (ProtocoloSiacolDto dto : votoService.contagem(pesquisaResultado)) {
			
			if (efeitoLegal && dto.teveMudancadeMerito()) {
				if (dto.votoDestaque()) {
//					protocoloVotado = new ProtocoloSiacol();
//					protocoloVotado = protocoloDao.getBy(dto.getId());
//					protocoloVotado.setStatus(StatusProtocoloSiacol.A_PAUTAR_DESTAQUE);
//					protocoloDao.update(protocoloVotado);
					protocoloVotado = atualizarStatusProtocolo(dto.getId(), StatusProtocoloSiacol.A_PAUTAR_DESTAQUE);
				} else {
//					protocoloVotado = new ProtocoloSiacol();
//					protocoloVotado = protocoloDao.getBy(dto.getId());
//					protocoloVotado.setStatus(StatusProtocoloSiacol.REVISAO_DECISAO_VIRTUAL);
//					protocoloDao.update(protocoloVotado);
					protocoloVotado = atualizarStatusProtocolo(dto.getId(), StatusProtocoloSiacol.REVISAO_DECISAO_VIRTUAL);
					gerarNumeroDeDecisao(protocoloVotado);
				}
				
				if (!dto.obteveQuorumMinimo()) {

//					protocoloVotado = new ProtocoloSiacol();
//					protocoloVotado = protocoloDao.getBy(dto.getId());
//					protocoloVotado.setStatus(StatusProtocoloSiacol.A_PAUTAR_SEM_QUORUM);
//					protocoloDao.update(protocoloVotado);
					protocoloVotado = atualizarStatusProtocolo(dto.getId(), StatusProtocoloSiacol.A_PAUTAR_SEM_QUORUM);

				}
				
			} else {
				if (dto.votoDestaque()) {
//					protocoloVotado = new ProtocoloSiacol();
//					protocoloVotado = protocoloDao.getBy(dto.getId());
//					protocoloVotado.setStatus(StatusProtocoloSiacol.A_PAUTAR_DESTAQUE);
//					protocoloDao.update(protocoloVotado);
					protocoloVotado = atualizarStatusProtocolo(dto.getId(), StatusProtocoloSiacol.A_PAUTAR_DESTAQUE);

				} else {

					if (dto.foiFavoravelAoInteressado()) {

//						protocoloVotado = new ProtocoloSiacol();
//						protocoloVotado = protocoloDao.getBy(dto.getId());
//						protocoloVotado.setStatus(StatusProtocoloSiacol.PARA_ASSINAR_VIRTUAL);
//						protocoloVotado.setClassificacaoFinal(ClassificacaoProtocoloPautaEnum.FAVORAVEL);
//						protocoloDao.update(protocoloVotado);
						protocoloVotado = atualizarStatusProtocolo(dto.getId(), StatusProtocoloSiacol.PARA_ASSINAR_VIRTUAL);
						protocoloVotado.setClassificacaoFinal(ClassificacaoProtocoloPautaEnum.FAVORAVEL);

						gerarNumeroDeDecisao(protocoloVotado);
					}

					if (dto.foiDesfavoravelAoInteressado()) {

						if (efeitoLegal) {
//							protocoloVotado.setStatus(StatusProtocoloSiacol.PARA_ASSINAR_VIRTUAL);
							protocoloVotado = atualizarStatusProtocolo(dto.getId(), StatusProtocoloSiacol.PARA_ASSINAR_VIRTUAL);
							protocoloVotado.setClassificacaoFinal(ClassificacaoProtocoloPautaEnum.DESFAVORAVEL);
							
							gerarNumeroDeDecisao(protocoloVotado);
						} else {
							protocoloVotado = atualizarStatusProtocolo(dto.getId(), StatusProtocoloSiacol.PARA_ASSINAR_VIRTUAL);
//							protocoloVotado.setStatus(StatusProtocoloSiacol.A_PAUTAR_PRESENCIAL);
						}
//						protocoloDao.update(protocoloVotado);

					}

					if (!dto.obteveQuorumMinimo()) {

//						protocoloVotado = new ProtocoloSiacol();
//						protocoloVotado = protocoloDao.getBy(dto.getId());
//						protocoloVotado.setStatus(StatusProtocoloSiacol.A_PAUTAR_SEM_QUORUM);
//						protocoloDao.update(protocoloVotado);
						protocoloVotado = atualizarStatusProtocolo(dto.getId(), StatusProtocoloSiacol.PARA_ASSINAR_VIRTUAL);


					}
				}
			}	

		}
	}

	private ProtocoloSiacol atualizarStatusProtocolo(Long idProtocolo, StatusProtocoloSiacol statusProtocoloSiacol) {
		ProtocoloSiacol protocoloVotado = new ProtocoloSiacol();
		protocoloVotado = protocoloDao.getBy(idProtocolo);
		protocoloVotado.setStatus(statusProtocoloSiacol);
		audita.alteraStatus(null, protocoloVotado, null);
		return protocoloDao.update(protocoloVotado);		
	}

	// FIXME: Excluir após apresentação em 16/02
	public String manipulaReuniao(TesteSiacolGenericDto dto) throws ParseException {

		if (dto.getNome().equals("finalizaReuniao")) {

			List<ReuniaoSiacol> listReunioes = new ArrayList<ReuniaoSiacol>();
			listReunioes = dao.getReunioesAptasEncerrar(DateUtils.DD_MM_YYYY.parse(dto.getDataManipularReuniao()));

			for (ReuniaoSiacol reuniao : listReunioes) {
				finalizaReuniaoVirtual(reuniao.getDataReuniao());
			}

		} else {
			dao.manipulaReuniao(dto);
		}
		return "Ação realizada sobre a reuniao para apresentacao: " + dto.getNome();

	}
	
	public List<ProtocoloSiacolDto> getProtocolosHomologacao(Long idDepartamento) {
		return protocoloConverter.toListDto(dao.getProtocolosHomologacao(idDepartamento));
	}

	public List<ProtocoloSiacolDto> getProtocolosDeferido(Long idDepartamento) {
		return protocoloConverter.toListDto(dao.getProtocolosDeferido(idDepartamento));
	}

	public List<ProtocoloSiacolDto> getProtocolosProvisorio(Long idDepartamento) {
		return protocoloConverter.toListDto(dao.getProtocolosProvisorio(idDepartamento));
	}

	public DocumentoGenericDto finalizaPauta(Long idPauta) {
		Documento pauta = documentoDao.recuperaDocumentosById(idPauta);

		StatusDocumento status = new StatusDocumento();
		status.setId(new Long(8));
		pauta.setStatusDocumento(status);

		pauta = documentoDao.update(pauta);

		List<DestinatarioEmailDto> listaDestinatario = new ArrayList<DestinatarioEmailDto>();
		DestinatarioEmailDto destinatario = new DestinatarioEmailDto();

		destinatario.setEmail("juan@calma.com.br");
		destinatario.setNome("Juan Diniz");
		listaDestinatario.add(destinatario);

		emailService.envia(emailUtil.populaEmail(listaDestinatario, "fernando@calma.com.br", "Pauta Finalizada", "Pauta No. " + idPauta + " finalizada!"));

		return documentoConverter.toDto(pauta);
	}

	public Boolean verificaPautaEmEdicao(Long idPauta, Long idFuncionario) {
		Documento pauta = documentoDao.recuperaDocumentosById(idPauta);

		return pauta.estaEmEdicao() && pauta.getResponsavel() == idFuncionario ? false : true;
	}

	public int getQuantidadeConsultaReunioes(PesquisaGenericDto dto) {
		return dao.quantidadeConsultaReunioes(dto);
	}

	public List<ReuniaoSiacolDto> getReunioesAbertas() {
		return converter.toListDto(dao.getReunioesAbertas());
	}

	public boolean atualizaReuniaoPresencial(ReuniaoSiacolDto reuniao, UserFrontDto user) {

		try {

			if (reuniao.heIniciarSessao()) {
				reuniao = acaoReuniaoService.iniciarSessao(reuniao);					
			} else if (reuniao.hePausarSessao()) {
				reuniao = acaoReuniaoService.pausarSessao(reuniao, user); 
			} else if (reuniao.heCancelarSessao()) {
				reuniao = acaoReuniaoService.cancelarSessao(reuniao, user); 
			} else if (reuniao.heEncerrarSessao()) {
				reuniao = acaoReuniaoService.encerrarSessao(reuniao, user); 
			}
			
			reuniao = acaoReuniaoService.ordenaPauta(reuniao);
			
			dao.update(converter.toModel(reuniao));

			auditaReuniaoFactory.auditaAcaoReuniao(reuniao, user);
		} catch (Exception e) {
			httpGoApi.geraLog("ReuniaoSiacolService || atualizaReuniaoPresencial", StringUtil.convertObjectToJson(reuniao), e);
			return false;
		}

		return true;
	}
			
	public void populaIndicadoresPlenaria(Long idDepartamento, Long idReuniao) {
		
		IndicadoresReuniaoSiacolDto indicadores = new IndicadoresReuniaoSiacolDto();

		int qtdEmpossados = personalidadeDao.getConselheirosEfetivosPorIdDepartamento(idDepartamento, new Long(99), false).size();
		int qtdLicenciados = personalidadeDao.getConselheirosEfetivosPorIdDepartamento(idDepartamento, new Long(1), false).size();
		int qtdPresentes = presencaReuniaoDao.getQuantidadeConselheirosPresentes(idReuniao);
		int qtdSuplentesDosLicenciados = personalidadeDao.getSuplentesDosLicenciadosPor(idDepartamento).size();
		int qtdAusentes = qtdEmpossados - qtdPresentes - qtdLicenciados + qtdSuplentesDosLicenciados;
		
		indicadores.setQtdPresentes(qtdPresentes);
		indicadores.setQtdAusentes(qtdAusentes);
		indicadores.setQtdLicenciados(qtdLicenciados);
		indicadores.setQtdEmpossados(qtdEmpossados);
		
		firebaseApi.salvarIndicadores(indicadores, idReuniao);
	}

	public List<DestinatarioEmailDto> buscaEmailsConselheiros(Long idDepartamento) {
		List<EmailConselheiro> listEmailConselheiro = new ArrayList<EmailConselheiro>();
		List<DestinatarioEmailDto> listDestinatario = new ArrayList<DestinatarioEmailDto>();
		
		listEmailConselheiro = personalidadeDao.buscaEmailConselheirosEnvioPautaPor(idDepartamento);
		for (EmailConselheiro dto : listEmailConselheiro) {
			if (dto != null) {
				DestinatarioEmailDto destinatario = new DestinatarioEmailDto();
				destinatario.setEmail(dto.getDescricao());
				destinatario.setNome(dto.getPessoa().getNome());
				listDestinatario.add(destinatario);
			}			
		}
		return listDestinatario;
	}

	public List<StatusDto> getStatusProtocoloSiacol() {
		List<StatusDto> listStatus = new ArrayList<StatusDto>();	
		
		StatusProtocoloSiacol[] statusProtocoloSiacol = StatusProtocoloSiacol.values();
		for (StatusProtocoloSiacol status : statusProtocoloSiacol) {  
			StatusDto statusProtocolo = new StatusDto();
			statusProtocolo.setDescricao(status.getDescricao());
			statusProtocolo.setId(status.getId());
			statusProtocolo.setTipo(status.getTipo());
			listStatus.add(statusProtocolo);
		}
		return listStatus;
	}

	public List<RelatorioReuniaoSiacolDto> getRelatoriosReuniaoPor(Long idReuniao) {
		List<RelatorioReuniaoSiacolDto> listaRelatorioDto = new ArrayList<RelatorioReuniaoSiacolDto>();
		
		List<RlReuniaoRelatorioSiacol> listaRelatorio = rlReuniaoRelatorioDao.getRelatoriosPor(idReuniao);
		
		if (listaRelatorio != null) {
			listaRelatorio.forEach(item -> {
				RelatorioReuniaoSiacolDto relatorioDto = new RelatorioReuniaoSiacolDto();
				relatorioDto.setUri(item.getRelatorio().getUri());
				relatorioDto.setNome(item.getTipo().getNome());
				relatorioDto.setTipo(item.getTipo().toString());
				relatorioDto.setNomeArquivoOriginal(item.getRelatorio().getNomeOriginal());
				relatorioDto.setNomeArquivo(item.getRelatorio().getNomeStorage());
				relatorioDto.setData(DateUtils.format(item.getRelatorio().getDataInclusao(), DateUtils.DD_MM_YYYY_HH_MM));
				listaRelatorioDto.add(relatorioDto);
			});
		} 
		
		return listaRelatorioDto;
	}

	public void setStatusPainelConselheiro(Long idReuniao, String status) {
		dao.atualizaStatusPainelReuniao(status, idReuniao);
	}

	public List<ParticipanteReuniaoSiacolDto> getMesaDiretora(Long idReuniao) {
		
		List<ParticipanteReuniaoSiacolDto> listaMesaDiretora = new ArrayList<ParticipanteReuniaoSiacolDto>();
		
		List<PresencaReuniaoSiacol> listaParticipantesMesaDiretora = presencaReuniaoDao.getMesaDiretora(idReuniao);
		
				
		listaParticipantesMesaDiretora.forEach(membro -> {
			
			ParticipanteReuniaoSiacolDto participante = new ParticipanteReuniaoSiacolDto();
			
			participante.setId(membro.getPessoa().getId());
			participante.setNome(membro.getPessoa().getNome());
			participante.setPapel(membro.getPapel());
			participante.setVotoMinerva(membro.isVotoMinerva());
			
			listaMesaDiretora.add(participante);
			
		});	
		
		return listaMesaDiretora;
	}

	public ParticipanteReuniaoSiacolDto getPresidenteDaMesaDiretora(Long idReuniao) {
		
		PresencaReuniaoSiacol membro = presencaReuniaoDao.getPresidenteDaMesaDiretora(idReuniao);
				
		if (membro != null) {
			ParticipanteReuniaoSiacolDto presidenteDaMesaDiretora = new ParticipanteReuniaoSiacolDto();
			
			presidenteDaMesaDiretora.setId(membro.getPessoa().getId());
			presidenteDaMesaDiretora.setNome(membro.getPessoa().getNome());
			presidenteDaMesaDiretora.setPapel(membro.getPapel());
			presidenteDaMesaDiretora.setVotoMinerva(membro.isVotoMinerva());
							
			return presidenteDaMesaDiretora;
		} else {
			return null;
		}
		
	}
	
	public List<RelatorioReuniaoSiacolDto> gerarRelatorioCoordenadoresNaTelaPor(Long idReuniao) {
		return relatorioDao.relatorioDeCoordenadoresEAdjuntosPorDepartamento(idReuniao);
	}
	
	public byte[] gerarRelatorioCoordenadoresPdfPor(Long idReuniao) {

		String nomeArquivo = idReuniao + " - " + TipoRelatorioSiacolEnum.RELATORIO_DE_COORDENADORES;
		ItextUtil.iniciarDocumentoParaDownload(nomeArquivo, ItextUtil.RETRATO);
		ItextUtil.adicionaCabecalhoPadrao();
		ItextUtil.adicionaRodapePadrao();
		ItextUtil.iniciarDocumento();
		ItextUtil.adicionaObjetoAoConteudoEFecha(geraConteudo.tabelaCoordenadoresEAdjuntos(relatorioDao.relatorioDeCoordenadoresEAdjuntosPorDepartamento(idReuniao)));
		
		return ItextUtil.getPdfBytes();
	}

	public boolean podeSalvarPauta(Long idReuniao) {
		return dao.podeSalvarPauta(dao.getBy(idReuniao));
	}

	public List<ReuniaoSiacolDto> reunioesParaCriacaoDeSumula(Long idDepartamento) {
		return converter.toListDto(dao.reunioesParaCriacaoDeSumula(idDepartamento));
	}
	
	public List<ReuniaoSiacolDto> reunioesParaCriacaoDeAta(Long idDepartamento, Long anoBusca) {
		return converter.toListDto(dao.reunioesParaCriacaoDeAta(idDepartamento, anoBusca));
	}
	
	public boolean enviarLembrate(ReuniaoSiacolDto reuniao){
		
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
		emailReuniao.setReuniao(converter.toModel(reuniao));
		
		mailEnvio = mailConverter.toDto(emailReuniao.getEmailEnvio());
		mailEnvio.setDataUltimoEnvio(new Date()); 
		mailEnvio.setDestinatarios(listDestinatario);
			
		String mensagem = "";
		mensagem += "<!DOCTYPE html>";
		mensagem += "<html>";
		mensagem += "<body>";
		mensagem += "</br><p>A reunião: " + reuniao.getDataReuniaoFormatado() + " da " + reuniao.getDepartamento().getNome() + " precisa de uma pauta</p>";
		mensagem += "</body>";
		mensagem += "</html>";
        
		mailEnvio.setMensagem(mensagem);

		mailService.envia(mailEnvio);
		return true;
		
	}

	public boolean naoAtingiuQuorumMinimo(ReuniaoSiacolDto reuniao) {
		return !(presencaReuniaoDao.getQuantidadeConselheirosPresentes(reuniao.getId()) >= reuniao.getQuorum());
	}

	public boolean retirarArquivoAlterarProfissional(GenericDto dto) {
		try {
			dto.setDataInicioFormatada(
					DateUtils.format(dao.getReuniaoPor(dto.getIdPauta()).getDataReuniao(), DateUtils.DD_MM_YYYY));
			profissionalDao.atualizarDataParaHomologacao(dto);
			return true;	
		} catch (Throwable e) {
			return false;		
		}		
	}


}
