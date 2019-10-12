package br.org.crea.siacol.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.NumeroDocumentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.RlDocumentoProtocoloSiacolDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolPresencaFactory;
import br.org.crea.commons.factory.siacol.AuditaSiacolProtocoloFactory;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.NumeroDocumento;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.siacol.PresencaReuniaoSiacol;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.VotoReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaItemPautaSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.OrderFilterSiacolProtocolo;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.models.siacol.enuns.StatusReuniaoSiacol;
import br.org.crea.commons.models.siacol.enuns.TipoRelatorioSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.siacol.dao.PresencaReuniaoSiacolDao;
import br.org.crea.siacol.dao.VotoReuniaoSiacolDao;
import br.org.crea.siacol.relatorio.GeraRelatorioSiacol;

public class AcaoReuniaoSiacolService {

	@Inject
	private RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloDao;

	@Inject
	private ProtocoloSiacolDao protocoloDao;
	
	@Inject
	private ProtocoloSiacolService protocoloService;

	@Inject
	private DocumentoDao documentoDao;

	@Inject
	private InteressadoDao interessadoDao;

	@Inject
	private PresencaReuniaoSiacolDao presencaReuniaoDao;
	
	@Inject
	private VotoReuniaoSiacolDao votoReuniaoSiacolDao;
				
	@Inject 
	private GeraRelatorioSiacol geraRelatorio;
	
	@Inject 
	private NumeroDocumentoDao numeroDocumentoDao;
	
	@Inject 
	private AuditaSiacolPresencaFactory auditaPresencaFactory;
	
	@Inject 
	private AuditaSiacolProtocoloFactory auditaProtocoloFactory;
	
	@Inject
	private HttpClientGoApi httpGoApi;

	public ReuniaoSiacolDto iniciarSessao(ReuniaoSiacolDto reuniao) {
		reuniao.setHoraInicio(new Date());
		reuniao.setStatus(StatusReuniaoSiacol.ABERTA);
		
		geraRelatorio.gera(reuniao, TipoRelatorioSiacolEnum.RELATORIO_INICIO_DA_REUNIAO);
		return reuniao;
	}

	public ReuniaoSiacolDto pausarSessao(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		
		geraRelatorio.gera(reuniao, TipoRelatorioSiacolEnum.RELATORIO_DE_PAUSA_DA_REUNIAO);
		presencaReuniaoDao.registrarSaidaDeTodos(reuniao.getId());

		// 4 - Após auditar, registrar de qual parte da reunião aquela presença foi registrada
		Long parteDaReuniao = reuniao.temParte() ? reuniao.getParte().longValue() + 1 : 1L;
		reuniao.setParte(parteDaReuniao);
		reuniao.setHouvePausa(true);
		reuniao.setStatus(StatusReuniaoSiacol.PAUSADA);
		
		presencaReuniaoDao.atualizarParteDaReuniaoNaListaDePresenca(reuniao.getId(), parteDaReuniao);
		
		//FIXME apos refactory da presença ajustar aqui
		List<PresencaReuniaoSiacol> listaPresenca = presencaReuniaoDao.getListPresenca(reuniao.getId());					
		for (PresencaReuniaoSiacol presenca : listaPresenca) {
			auditaPresencaFactory.auditaAcaoPresenca(presenca.getReuniao().getId(), presenca.getPessoa().getId(), "PAUSA", user);
		}	
		
		return reuniao;
	}

	public ReuniaoSiacolDto cancelarSessao(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		 
		geraRelatorio.gera(reuniao, TipoRelatorioSiacolEnum.RELATORIO_DE_CANCELAMENTO_DA_REUNIAO);
		votoReuniaoSiacolDao.apagarTodosOsVotos(reuniao.getId());
		presencaReuniaoDao.deletaRegistrosDePresenca(reuniao.getId());
		retornaProtocolosAStatusAnterior(reuniao, user);
		rlDocumentoProtocoloDao.deletaTodosOsItens(reuniao.getIdsPautas());
		
		reuniao.setHoraFim(new Date());
		reuniao.setStatus(StatusReuniaoSiacol.CANCELADA);
		reuniao.setPauta(null);
		reuniao.setExtraPauta(null);
		return reuniao;
	}
	
	public ReuniaoSiacolDto encerrarSessao(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		reuniao.setHoraFim(new Date());
		reuniao.setStatus(StatusReuniaoSiacol.FECHADA);
		
		atualizarProtocolosAoEncerrarReuniaoPresencial(reuniao, user);
		gerarNumeroDeDecisao(reuniao, user);
		
		geraRelatorio.gera(reuniao, TipoRelatorioSiacolEnum.RELATORIO_FIM_DA_REUNIAO);
		geraRelatorio.gera(reuniao, TipoRelatorioSiacolEnum.RELATORIO_FIM_DE_QUORUM);
				
		presencaReuniaoDao.registrarSaidaDeTodos(reuniao.getId());
		
		if (reuniao.isHouvePausa()) {
			Long parteDaReuniao = reuniao.temParte() ? reuniao.getParte().longValue() + 1 : 1L;
			reuniao.setParte(parteDaReuniao);
			
			presencaReuniaoDao.atualizarParteDaReuniaoNaListaDePresenca(reuniao.getId(), parteDaReuniao);
		} 
		geraRelatorio.gera(reuniao, TipoRelatorioSiacolEnum.RELATORIO_DE_COMPARECIMENTO);
		
		
		return reuniao;
	}
	
	public ReuniaoSiacolDto ordenaPauta(ReuniaoSiacolDto reuniao) {
		List<GenericSiacolDto> listOrdenacaoPauta = new ArrayList<GenericSiacolDto>();
		GenericSiacolDto ordenacao = new GenericSiacolDto();
		ordenacao.setOrdemFiltro(new Long(2));
		
		ordenacao.setNome("ASSUNTO SIACOL");
		ordenacao.setFiltroProtocolo(OrderFilterSiacolProtocolo.ORDER_BY_ASSUNTO_SIACOL);
		listOrdenacaoPauta.add(ordenacao);
		
		reuniao.setOrdenacaoPauta(listOrdenacaoPauta);
		
		List<String> listDigitos = new ArrayList<>();
		listDigitos.add("");
		reuniao.setListDigitoExclusaoProtocolo(listDigitos);
		return reuniao;
	}

	private void gerarNumeroDeDecisao(ReuniaoSiacolDto reuniao, UserFrontDto usuario) {
		
		String numeroReal = "";
		
		PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
		pesquisa.setSomenteProtocolos(false);
		pesquisa.setIdsPautas(reuniao.getIdsPautas());
		pesquisa.setTemSolicitacaoVistas(false);
		pesquisa.setStatus(new Long(1)); 
		
		List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.pesquisa(pesquisa);
		
		if (listaItens.size() > 0) {
			for (RlDocumentoProtocoloSiacol item : listaItens) {
				
				NumeroDocumento numeroDocumento = numeroDocumentoDao.getNumeroDocumento(reuniao.getDepartamento().getId(), new Long(1102));
				
				if (numeroDocumento != null) {
					if (numeroDocumento.isTem_ano()) {
						numeroReal = numeroDocumento.getNumero() + "/" + numeroDocumento.getAno() + " - " + numeroDocumento.getDepartamento().getSigla(); 
					} else {
						numeroReal = numeroDocumento.getNumero() + " - " + numeroDocumento.getDepartamento().getSigla();
					}
					
					numeroDocumento.setNumero(numeroDocumento.getNumero()+1);
					numeroDocumentoDao.update(numeroDocumento);
				}
				
				item.setNumeroDocumento(numeroReal);			
				rlDocumentoProtocoloDao.update(item);
				
				if (item.temProtocolo()) {
					ProtocoloSiacol protocolo = protocoloDao.getBy(item.getProtocolo().getId());
					protocolo.setNumeroDecisao(numeroReal);
					protocoloDao.update(protocolo);
					
					atualizaNumeroDecisaoNoDocumento(numeroReal, protocolo.getNumeroProtocolo());
					
					auditaProtocoloFactory.auditaJulgamento(protocolo, item, usuario);
				}			
			}
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

	private void atualizarProtocolosAoEncerrarReuniaoPresencial(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		
		try {
			atualizarProtocolosDeItensNaoVotadosSemVistas(reuniao, user);		
			atualizarProtocolosDeItensNaoVotadosComVistas(reuniao, user);
			
			atualizarProtocolosDeItensVotadosSemVistas(reuniao, user);
			atualizaProtocolosDeItensVotadosComVistas(reuniao, user);
			atualizaItensVotadosSemVistasComObservacao(reuniao, user);
			atualizaItensVotadosSemVistasComEnquete(reuniao, user);
			atualizaProtocolosDeItensVotadosComUrgencia(reuniao);
			atualizaItensHomologacaoAdReferendum(reuniao, user);
			atualizaItensRetiradosDaPauta(reuniao, user);
			
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizarProtocolosAoEncerrarReuniaoPresencial", StringUtil.convertObjectToJson(reuniao), e);
		}
		
	}

	private void atualizarProtocolosDeItensNaoVotadosSemVistas(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			pesquisa.setTemVistasConcedida(false);
			pesquisa.setStatus(new Long(99));  // confirmar se essa pesquisa atende
			
			alteraStatusEAuditaAlteracaoDeListaDeProtocolos(user, rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa), StatusProtocoloSiacol.A_PAUTAR_SEM_QUORUM);
		
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizarProtocolosDeItensNaoVotadosSemVistas", StringUtil.convertObjectToJson(reuniao), e);
		}
	}
	
	private void atualizarProtocolosDeItensNaoVotadosComVistas(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			pesquisa.setTemVistasConcedida(true);
			pesquisa.setStatus(new Long(99));  
			
			List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa);
			
			for (RlDocumentoProtocoloSiacol item : listaItens) {
				ProtocoloSiacol protocolo = protocoloDao.getBy(item.getProtocolo().getId());
				
	//			1 - itens com protocolos com vistas nao votados com relatorio assinado
				List<Documento> listaRelatoriosVista = documentoDao.getDocumentosPorTipoENumeroProtocolo(new Long(1108), protocolo.getNumeroProtocolo());
				if (listaRelatoriosVista.size() > 0) {
					// verificar se esta assinado
					protocoloService.atualizaStatus(protocolo, StatusProtocoloSiacol.A_PAUTAR_VISTAS, user, null);
					
				} else {
					
					if (!protocolo.getIdResponsavel().equals(item.getIdPessoaVista())) {
						protocolo.setIdResponsavel(item.getIdPessoaVista());
						IInteressado interessado = interessadoDao.buscaInteressadoBy(item.getIdPessoaVista());
						protocolo.setNomeResponsavel(interessado.getNome());
						protocolo.setRecebido(false);
						protocolo.setDataRecebimento(null);
					}
					
					protocoloService.atualizaStatus(protocolo, StatusProtocoloSiacol.ANALISE_VISTAS_A_PAUTAR, user, null);
					protocoloDao.update(protocolo);		
				}
			} 
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizarProtocolosDeItensNaoVotadosComVistas", StringUtil.convertObjectToJson(reuniao), e);
		}
	}

	private void atualizarProtocolosDeItensVotadosSemVistas(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			pesquisa.setTemVistasConcedida(false); // conferir se isto basta
			pesquisa.setStatus(new Long(1));
			pesquisa.setTemUrgencia(false);
			
			List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa);
			
			for (RlDocumentoProtocoloSiacol item : listaItens) {
				ProtocoloSiacol protocolo = protocoloDao.getBy(item.getProtocolo().getId());
							
				if (item.houveEmpate()) {
					VotoReuniaoSiacol votoDeMinerva = votoReuniaoSiacolDao.getVotoByIdPessoaByIdItemPauta(item.getIdPessoaMinerva(), item.getId());
					if (votoDeMinerva != null) { // em caso de empate, tem que ter voto de minerva, mas se por algum motivo não tiver, evitamos null pointer aqui
						item.desempate(votoDeMinerva.getVoto());
					}
				}
				
				if (item.maioriaDosVotosEhSim()) {
					GenericSiacolDto dto = new GenericSiacolDto();
					dto.setIdProtocolo(item.getProtocolo().getId());
					dto.setClassificacaoFinal(protocolo.getClassificacao());
					dto.setStatus(StatusProtocoloSiacol.PARA_ASSINAR_PRESENCIAL);
					protocoloService.alteraSituacao(dto, dto.getIdProtocolo(), user);
				} else {
					protocoloService.atualizaStatus(protocolo, StatusProtocoloSiacol.REVISAO_DECISAO_PRESENCIAL, user, null);
				}
			} 
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizarProtocolosDeItensVotadosSemVistas", StringUtil.convertObjectToJson(reuniao), e);
		}
	}
	
	private void atualizaProtocolosDeItensVotadosComVistas(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			pesquisa.setTemVistasConcedida(true);
			pesquisa.setStatus(new Long(1));
			pesquisa.setTemUrgencia(false);
			
			alteraStatusEAuditaAlteracaoDeListaDeProtocolos(user, rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa), StatusProtocoloSiacol.REVISAO_VISTAS_VOTADO);
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizaProtocolosDeItensVotadosComVistas", StringUtil.convertObjectToJson(reuniao), e);
		}
	}

	private void atualizaItensVotadosSemVistasComObservacao(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			pesquisa.setTemVistasConcedida(false);
			pesquisa.setStatus(new Long(1)); 
			pesquisa.setTemObservacao(true);
			pesquisa.setTemUrgencia(false);
			
			alteraStatusEAuditaAlteracaoDeListaDeProtocolos(user, rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa), StatusProtocoloSiacol.REVISAO_DECISAO_PRESENCIAL);
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizaItensVotadosSemVistasComObservacao", StringUtil.convertObjectToJson(reuniao), e);
		}
	}
	
	private void atualizaItensVotadosSemVistasComEnquete(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			pesquisa.setTemVistasConcedida(false);
			pesquisa.setStatus(new Long(1)); 
			pesquisa.setTemEnquete(true);
			pesquisa.setTemUrgencia(false);
					
			alteraStatusEAuditaAlteracaoDeListaDeProtocolos(user, rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa), StatusProtocoloSiacol.REVISAO_DECISAO_PRESENCIAL);
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizaItensVotadosSemVistasComObservacao", StringUtil.convertObjectToJson(reuniao), e);
		}
	}
	
	private void atualizaProtocolosDeItensVotadosComUrgencia(ReuniaoSiacolDto reuniao) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			pesquisa.setStatus(new Long(1));
			pesquisa.setTemUrgencia(true);
			
			List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa);
			
			for (RlDocumentoProtocoloSiacol item : listaItens) {
				ProtocoloSiacol protocolo = protocoloDao.getBy(item.getProtocolo().getId());
				
				protocolo.setUrgenciaVotado(true);
				protocoloDao.update(protocolo);		
			}
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizaProtocolosDeItensVotadosComUrgencia", StringUtil.convertObjectToJson(reuniao), e);
		}
		
	}
	
	private void atualizaItensHomologacaoAdReferendum(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			pesquisa.setStatus(new Long(1)); 
			pesquisa.setTemUrgencia(false);
			
			List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa);
			
			for (RlDocumentoProtocoloSiacol item : listaItens) {
				ProtocoloSiacol protocolo = protocoloDao.getBy(item.getProtocolo().getId());
										
				if (protocolo.temAdReferendum()) {
					if (protocolo.getAdReferendum()) {
						protocoloService.atualizaStatus(protocolo, StatusProtocoloSiacol.REVISAO_DECISAO_PRESENCIAL, user, null);
					}				
				}			
			}
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizaItensHomologacaoAdReferendum", StringUtil.convertObjectToJson(reuniao), e);
		}
	}
	
	private void atualizaItensRetiradosDaPauta(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			pesquisa.setStatus(new Long(5));
			
			List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa);
			
			for (RlDocumentoProtocoloSiacol item : listaItens) {
				ProtocoloSiacol protocolo = protocoloDao.getBy(item.getProtocolo().getId());
										
				protocoloService.atualizaStatus(protocolo, StatusProtocoloSiacol.RETIRADO_DE_PAUTA, user, null);
			}
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || atualizaItensRetiradosDaPauta", StringUtil.convertObjectToJson(reuniao), e);
		}
	}
	
	private void alteraStatusEAuditaAlteracaoDeListaDeProtocolos(UserFrontDto user, List<RlDocumentoProtocoloSiacol> listaItens, StatusProtocoloSiacol statusProtocolo) {
		try {
			for (RlDocumentoProtocoloSiacol item : listaItens) {
				ProtocoloSiacol protocolo = protocoloDao.getBy(item.getProtocolo().getId());
										
				protocoloService.atualizaStatus(protocolo, statusProtocolo, user, null);
			}
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || alteraStatusEAuditaAlteracaoDeListaDeProtocolos", StringUtil.convertObjectToJson(listaItens), e);
		}
	}

	private void retornaProtocolosAStatusAnterior(ReuniaoSiacolDto reuniao, UserFrontDto user) {
		try {
			PesquisaItemPautaSiacolDto pesquisa = new PesquisaItemPautaSiacolDto();
			pesquisa.setSomenteProtocolos(true);
			pesquisa.setIdsPautas(reuniao.getIdsPautas());
			
			List<RlDocumentoProtocoloSiacol> listaItens = rlDocumentoProtocoloDao.pesquisaEncerramento(pesquisa);
			
			for (RlDocumentoProtocoloSiacol item : listaItens) {
				ProtocoloSiacol protocolo = protocoloDao.getBy(item.getProtocolo().getId());
				
				protocoloService.atualizaStatus(protocolo, protocolo.getUltimoStatus(), user, null);
			} 
		} catch (Exception e) {
			httpGoApi.geraLog("AcaoReuniaoSiacolService || retornaProtocolosAStatusAnterior", StringUtil.convertObjectToJson(reuniao), e);
		}
	}

}
