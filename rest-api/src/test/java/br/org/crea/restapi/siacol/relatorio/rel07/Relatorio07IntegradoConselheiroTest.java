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

public class Relatorio07IntegradoConselheiroTest {
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
//	public void rel07ConselheiroTesteIntegrado() {
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
//		RelSiacol07Dto linhaAssunto;
//		List<StatusRel07> listaStatus;
//		
//		// FIXME fazer querys para o caso de não passar assunto
//		for (AssuntoDto assunto : assuntos) {
//			linhaAssunto = new RelSiacol07Dto();
//			linhaAssunto.setAssunto(assunto.getDescricao());
//			
//			listaStatus = new ArrayList<StatusRel07>();
//			
//			if (ehPerfilConselheiro(perfil)) {
//				listaStatus.add(populaRecebidosConselheiro(pesquisa, assunto.getId()));
//				listaStatus.add(populaAnaliseConselheiro(pesquisa, assunto.getId(), populaListaProtocolos(listaRecebimentoRel07)));
//			}
//			
//			linhaAssunto.setStatus(listaStatus);
//			listaRelatorio.add(linhaAssunto);
//		}
//		
//		return contagemEstatistica(listaRelatorio);
//	}
//	
//	private StatusRel07 populaRecebidosConselheiro(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
//		StatusRel07 status = new StatusRel07();
//		status.setNome("RECEBER");
//		
//		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
//		Date dataRecebimento = new Date();
//		Date dataInicioRecebimento = new Date();
//		
//		List<ProtocolosRel07RecebimentoDto> listaRecebimentos = dao.getRecebimentoSiacol(pesquisa, idAssuntoSiacol);
//		
//		for (ProtocolosRel07RecebimentoDto recebimento : listaRecebimentos) {
//			ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
//			protocolo.setNumeroProtocolo(recebimento.getNumeroProtocolo());
//			dataRecebimento = recebimento.getData();
//			
//			ProtocolosRel07RecebimentoDto saida = dao.getDistribuicaoConselheiroOuImpedimento(pesquisa, idAssuntoSiacol, recebimento.getIdAuditoria());
//			
//			if (saida != null) {
//				dataInicioRecebimento = saida.getData();
//			}
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
//	private StatusRel07 populaAnaliseConselheiro(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, List<String> listaProtocolosRecebimento) {
//		StatusRel07 status = new StatusRel07();
//		status.setNome("ANÁLISE");
//		
//		Date hoje = new Date();
//		Date dataFimAnalise = null;
//		ProtocolosRel07RecebimentoDto saidaAnaliseConselheiro = null;
//		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
//						
//		List<ProtocolosRel07RecebimentoDto> listaRecebimentoSiacol = dao.getRecebimentoSiacol(pesquisa, idAssuntoSiacol);
//		
//		for (ProtocolosRel07RecebimentoDto recebimentoAnalise : listaRecebimentoSiacol) { 
//			ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
//			protocolo.setNumeroProtocolo(recebimentoAnalise.getNumeroProtocolo());
//							
//			// 1º ID DEPOIS DO RECEBIMENTO NOS CASOS ABAIXO, SE NAO ACHAR NA PRIMEIRA QUERY, PROCURAR NA PROXIMA
//			
//			// FIXME ASSINA E PAUTA
//			// VERIFICAR EVENTO DE ALTERACAO DE STATUS ONDE STATUS NOVO É IGUAL A ALGUM A_PAUTAR E SUAS DERIVAÇÕES
//			saidaAnaliseConselheiro = dao.getSaidaAnaliseAssinaEPautaConselheiro(pesquisa, idAssuntoSiacol);
//			
//			if (saidaAnaliseConselheiro == null) {
//				// FIXME IMPEDIMENTO
//				// VERIFICAR SE TEM EVENTO DE IMPEDIMENTO(37)
//				saidaAnaliseConselheiro = dao.getSaidaAnaliseImpedimentoConselheiro(pesquisa, idAssuntoSiacol);
//			}
//			
//			if (saidaAnaliseConselheiro == null) {
//				// FIXME DISTRIBUICAO ANALISTA
//				// VERIFICAR EVENTO DE ALTERACAO DE STATUS ONDE STATUS NOVO É IGUAL A DEVOLUÇÃO
//				saidaAnaliseConselheiro = dao.getSaidaAnaliseDistribuicao(pesquisa, idAssuntoSiacol);
//			}
//			
//			if (saidaAnaliseConselheiro == null) {
//				// FIXME INTERVENCAO COORDENADOR CAMARA
//				List<ProtocolosRel07RecebimentoDto> lista = dao.getDistribuicaoPorOutroUsuario(pesquisa, idAssuntoSiacol);
//				if (lista != null) {
//					if (lista.size() > 0) {
//						saidaAnaliseConselheiro = lista.get(0);
//					}
//				}
//			}
//			
//			if (saidaAnaliseConselheiro != null) {
//				dataFimAnalise = saidaAnaliseConselheiro.getData();
//			} else {
//				dataFimAnalise = hoje;
//			}
//			
//			Date dataInicioAnalise = recebimentoAnalise.getData();
//			Long diferenca = DateUtils.getDiferencaDiasEntreDatas(dataInicioAnalise, dataFimAnalise);
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
//	private boolean ehPerfilConselheiro(String perfil) {
//		return perfil.equals("siacolconselheiro") || perfil.equals("siacolcoordenadorcamara");
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

		
}
