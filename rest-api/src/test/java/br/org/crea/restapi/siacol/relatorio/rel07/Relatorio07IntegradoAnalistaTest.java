package br.org.crea.restapi.siacol.relatorio.rel07;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol07Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07RecebimentoDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol07Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.StatusRel07;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.EstatisticaUtil;
import br.org.crea.commons.util.ListUtils;

public class Relatorio07IntegradoAnalistaTest {
//
//	static RelatorioSiacol07Dao dao;
//	private static EntityManager em = null;
//	List<ProtocolosRel07RecebimentoDto> listaRecebimentoRel07 = null;
//	
//	@Before
//	public void inicio() {
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
//		em = factory.createEntityManager();
//		dao = new RelatorioSiacol07Dao();
//		dao.setEntityManager(em);
//	}
//	
//	@After
//	public void fim() {
//		em.close();
//	}
//	
//	private PesquisaRelatorioSiacolDto populaPesquisa() {
//		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
//		
//		List<PessoaDto> responsaveis = new ArrayList<PessoaDto>();
//		PessoaDto responsavel = new PessoaDto();
//		responsavel.setId(1983103508L);
//		responsavel.setPerfil("siacolanalista");
//		responsaveis.add(responsavel);
//		pesquisa.setResponsaveis(responsaveis);
//
//		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
//		DepartamentoDto departamento = new DepartamentoDto();
//		departamento.setId(1206L);
//		departamento.setNome("CAMARA CIVIL");
//		departamentos.add(departamento);
//		pesquisa.setDepartamentos(departamentos);
//		
//		List<AssuntoDto> assuntos = new ArrayList<AssuntoDto>();
//		AssuntoDto assunto = new AssuntoDto();
//		assunto.setDescricao("Assunto 1");
//		assunto.setId(52L);
//		assuntos.add(assunto);
//		pesquisa.setAssuntos(assuntos);
//		
//		pesquisa.setAno("2018");
//		List<String> meses = new ArrayList<String>();
//		meses.add("11");
//		pesquisa.setMeses(meses);
//		
//		return pesquisa;
//	}
//	
//	@Test
//	public void rel07AnalistaTesteIntegrado() {
//		
//		try {
//			List<RelSiacol07Dto> listaRelatorio = detalhamentoDoHistoricoDeProducaoDaQuantidadeDeProtocolosPorAnalistaOuConselheiroEPorAssunto(populaPesquisa());
//			
//			for (RelSiacol07Dto relatorio : listaRelatorio) {
//				System.out.println(relatorio.getAssunto());
//				for (StatusRel07 status : relatorio.getStatus()) {
//					System.out.println(status.getNome());
//					
//					System.out.println("Minímo | Máximo | Média | Moda | Desvio Padrão | Quantidade");
//					System.out.println(status.getMin() + " | " + status.getMax() + " | " + status.getMedia() + " | " + status.getModal() + " | " + status.getDesv() + " | " + status.getQtd());
//					
//					for (ProtocolosRel07Dto protocolo : status.getListaProtocolos()) {
//						System.out.println(protocolo.getNumeroProtocolo() + " | " + protocolo.getDiferencaEmDias());
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//	}
//	
//	public List<RelSiacol07Dto> detalhamentoDoHistoricoDeProducaoDaQuantidadeDeProtocolosPorAnalistaOuConselheiroEPorAssunto(PesquisaRelatorioSiacolDto pesquisa) {
//
//		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
//		String perfil = pesquisa.getResponsaveis().get(0).getPerfil();
//		
//		List<RelSiacol07Dto> listaRelatorio = new ArrayList<RelSiacol07Dto>();
//		
//		// FIXME fazer querys para o caso de não passar assunto
//		for (AssuntoDto assunto : assuntos) {
//			RelSiacol07Dto linhaAssunto = new RelSiacol07Dto();
//			linhaAssunto.setAssunto(assunto.getDescricao());
//			
//			List<StatusRel07> listaStatus = new ArrayList<StatusRel07>();
//			
//			if (ehPerfilAnalista(perfil)) {
//				listaStatus.add(populaRecebidosAnalista(pesquisa, assunto.getId()));
//				listaStatus.add(populaAnaliseAnalista(pesquisa, assunto.getId(), populaListaProtocolos(listaRecebimentoRel07)));
//				listaStatus.add(populaPausadoAnalista(pesquisa, populaListaProtocolos(listaRecebimentoRel07)));
//				listaStatus.add(populaTempoVinculoAnalista(pesquisa, assunto.getId()));
//			}
//			
//			linhaAssunto.setStatus(listaStatus);
//			listaRelatorio.add(linhaAssunto);
//		}
//		
//		return contagemEstatistica(listaRelatorio);
//	}
//	
//	private StatusRel07 populaRecebidosAnalista(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
//		StatusRel07 status = new StatusRel07();
//		status.setNome("RECEBER");
//		
//		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
//		Date dataRecebimento = new Date();
//		Date dataInicioRecebimento = new Date();
//		
//		List<ProtocolosRel07RecebimentoDto> listaRecebimentos = new ArrayList<ProtocolosRel07RecebimentoDto>();
//			
//		// nao recebido, sofreu intervencao (usuario remetente <> usuario que executou ação na auditoria e evento distribuicao)
//		List<ProtocolosRel07RecebimentoDto> listaDistribuicaoOutroUsuario = dao.getDistribuicaoPorOutroUsuario(pesquisa, idAssuntoSiacol);
//		listaRecebimentos.addAll(listaDistribuicaoOutroUsuario);
//		
//		List<ProtocolosRel07RecebimentoDto> listaRecebimentoSiacol = dao.getRecebimentoSiacol(pesquisa, idAssuntoSiacol);
//		listaRecebimentos.addAll(listaRecebimentoSiacol);
//	
//		for (ProtocolosRel07RecebimentoDto recebimento : listaRecebimentos) {
//			ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
//			protocolo.setNumeroProtocolo(recebimento.getNumeroProtocolo());
//			dataRecebimento = recebimento.getData();
//			
//			
//			ProtocolosRel07RecebimentoDto distribuicao = dao.getDistribuicaoAnalista(pesquisa, recebimento.getNumeroProtocolo(), recebimento.getIdAuditoria());
//			
//			if (distribuicao == null) {
//				ProtocolosRel07RecebimentoDto tramiteEnvio = dao.getTramiteEnvioAnalista(pesquisa, recebimento.getNumeroProtocolo(), recebimento.getIdAuditoria());
//				dataInicioRecebimento = tramiteEnvio.getData();
//			} else {
//				dataInicioRecebimento = distribuicao.getData();
//			}
//			
//			Long diferenca = DateUtils.getDiferencaDiasEntreDatas(dataInicioRecebimento, dataRecebimento);
//			protocolo.setDiferencaEmDias(diferenca.intValue());
//			listaProtocolos.add(protocolo);
//		}
//		
//		listaRecebimentoRel07 = listaRecebimentos;
//		
//		status.setListaProtocolos(listaProtocolos);
//		
//		return status;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private StatusRel07 populaAnaliseAnalista(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, List<String> listaProtocolosRecebimento) {
//		StatusRel07 status = new StatusRel07();
//		status.setNome("ANÁLISE");
//		
//		Date hoje = new Date();
//		Date dataFimAnalise = null;
//		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
//		
//		listaProtocolosRecebimento = (List<String>) ListUtils.removerDuplicidade(listaProtocolosRecebimento);
//		
//		List<ProtocolosRel07RecebimentoDto> listaEntradaAnalise = dao.getEntradaAnaliseAnalista(pesquisa, idAssuntoSiacol, listaProtocolosRecebimento);
//		
//		for (ProtocolosRel07RecebimentoDto recebimentoAnalise : listaEntradaAnalise) { 
//			ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
//			protocolo.setNumeroProtocolo(recebimentoAnalise.getNumeroProtocolo());
//			
//			ProtocolosRel07RecebimentoDto saidaAnalise = dao.getSaidaAnaliseAnalista(pesquisa, idAssuntoSiacol, recebimentoAnalise);
//			
//			if (saidaAnalise != null) {
//				dataFimAnalise = saidaAnalise.getData();
//				
//			} else {
//				dataFimAnalise = hoje;
//			}
//			Date dataInicioAnalise = recebimentoAnalise.getData();
//			Long diferenca = DateUtils.getDiferencaDiasEntreDatas(dataInicioAnalise, dataFimAnalise);
//			protocolo.setDiferencaEmDias(diferenca.intValue());
//			listaProtocolos.add(protocolo);
//		}
//		
//		// FIXME somar o tempo de análise geral para cada protocolo, sem duplicidade
//		
//		status.setListaProtocolos(listaProtocolos);
//		
//		return status;
//	}
//	
//	private StatusRel07 populaPausadoAnalista(PesquisaRelatorioSiacolDto pesquisa, List<String> listaRecebimento) {
//		StatusRel07 status = new StatusRel07();
//		status.setNome("PAUSADO");
//		
//		Date hoje = new Date();
//		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
//		Date dataFimPausa = null;
//		
//		List<ProtocolosRel07RecebimentoDto> listaEntradaPausados = dao.getEntradaPausadosAnalista(pesquisa, listaRecebimento);
//		
//		for (ProtocolosRel07RecebimentoDto entradaPausado : listaEntradaPausados) {
//			
////			ProtocolosRel07RecebimentoDto pausado = dao.getSaidaPausadosAnalista(pesquisa, entradaPausado);
////			ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
////			protocolo.setNumeroProtocolo(entradaPausado.getNumeroProtocolo());
////			
////			if (pausado != null) {
////				dataFimPausa = pausado.getData();
////			} else {
////				dataFimPausa = hoje;
////			}
////			Date dataInicioPausa = entradaPausado.getData();
////			Long diferenca = DateUtils.getDiferencaDiasEntreDatas(dataInicioPausa, dataFimPausa);
////			protocolo.setDiferencaEmDias(diferenca.intValue());
//		}
//		
//		status.setListaProtocolos(listaProtocolos);
//		
//		return status;
//	}
//	
//	private StatusRel07 populaTempoVinculoAnalista(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
//		StatusRel07 status = new StatusRel07();
//		status.setNome("TEMPO VINCULO");
//		
//		Date hoje = new Date();
//		Date dataInicioPendenteVinculo = null;
//		Date dataSaidaPendenteVinculo = null;
//
//		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
//		
//		// FIXME entrada - status novo = pendente vinculo
//		List<ProtocolosRel07RecebimentoDto> listaEntradaVinculo = dao.getEntradaVinculoAnalista(pesquisa, idAssuntoSiacol);
//
//		for (ProtocolosRel07RecebimentoDto entradaVinculo : listaEntradaVinculo) {
//			ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
//			protocolo.setNumeroProtocolo(entradaVinculo.getNumeroProtocolo());
//
//			dataInicioPendenteVinculo = entradaVinculo.getData();
//			
//			// FIXME saida - status antigo = pendente vinculo, id > entrada
//			ProtocolosRel07RecebimentoDto saidaVinculo = dao.getSaidaVinculoAnalista(pesquisa, entradaVinculo.getIdAuditoria());
//			
//			if (saidaVinculo != null) {
//				dataSaidaPendenteVinculo = saidaVinculo.getData();
//			} else {
//				dataSaidaPendenteVinculo = hoje;
//			}
//			
//			Long diferenca = DateUtils.getDiferencaDiasEntreDatas(dataInicioPendenteVinculo, dataSaidaPendenteVinculo);
//			protocolo.setDiferencaEmDias(diferenca.intValue());
//			listaProtocolos.add(protocolo);
//		}
//		
//		status.setListaProtocolos(listaProtocolos);
//		
//		return status;
//	}
//		
//	private List<String> populaListaProtocolos(List<ProtocolosRel07RecebimentoDto> listaRecebimento) {
//		List<String> listaProtocolos = new ArrayList<String>();
//		
//		for (ProtocolosRel07RecebimentoDto recebimento : listaRecebimento) {
//			listaProtocolos.add(recebimento.getNumeroProtocolo());
//		}
//		return listaProtocolos;
//	}
//		
//	private List<RelSiacol07Dto> contagemEstatistica(List<RelSiacol07Dto> listaRelatorio) {
//		
//		for (RelSiacol07Dto relatorio : listaRelatorio) {
//			
//			for (StatusRel07 status : relatorio.getStatus()) {
//				List<Integer> lista = status.getListaProtocolos().stream().map(p -> p.getDiferencaEmDias()).collect(Collectors.toList());
//					
//				status.setMin(EstatisticaUtil.valorMinimo(lista));
//				status.setMax(EstatisticaUtil.valorMaximo(lista));
//				Double media = EstatisticaUtil.mediaInt(lista);
//				status.setMedia(media.intValue());
//				status.setModal(0); // FIXME MODA
//				Double valorDesvioPadrao = EstatisticaUtil.desvioPadrao(lista);
//				status.setDesv(valorDesvioPadrao.intValue());
//				status.setQtd(status.getListaProtocolos().size());
//			}			
//		}		
//		
//		return listaRelatorio;
//	}
//	
//	private boolean ehPerfilAnalista(String perfil) {
//		return perfil.equals("siacolestagiarioAI") || perfil.equals("siacolanalista") || perfil.equals("siacolanalistaadministrador") || perfil.equals("siacolanalistaautoinfracao") || perfil.equals("siacolanalistaregional") || perfil.equals("siacolanalistacomissao");
//	}	
}
