package br.org.crea.restapi.siacol.relatorio.rel09;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol09Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol09Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.StatusQtdDto;
import br.org.crea.commons.util.StringUtil;

public class Relatorio09IntegradoTest {

	static RelatorioSiacol09Dao dao;
	static AssuntoDao assuntoCorporativoDao;
	static AssuntoSiacolDao assuntoDao;
	static AssuntoConverter assuntoConverter;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioSiacol09Dao();
	    assuntoDao = new AssuntoSiacolDao();
	    assuntoCorporativoDao = new AssuntoDao();
	    assuntoConverter = new AssuntoConverter();
		dao.setEntityManager(em);
		assuntoCorporativoDao.setEntityManager(em);
		assuntoDao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	private PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
		
		List<PessoaDto> responsaveis = new ArrayList<PessoaDto>();
		PessoaDto responsavel = new PessoaDto();
		responsavel.setId(2000105584L);
		responsavel.setPerfil("siacolanalista");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);

		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1208L);
		departamento.setNome("CAMARA QUIMICA");
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);
		
		return pesquisa;
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			List<RelSiacol09Dto> relatorio = relacaoDetalhadaPorTipoPendenteDeAnalise(populaPesquisa());
			
			relatorio.forEach(linhaAssunto -> {
				System.out.println(linhaAssunto.getAssunto());
				linhaAssunto.getStatus().forEach(colunaStatus -> {
					if (colunaStatus.getTotal() != 0) {
						System.out.println(colunaStatus.getStatus() + " | " + colunaStatus.getTotal());
					}
				});
			});
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public List<RelSiacol09Dto> relacaoDetalhadaPorTipoPendenteDeAnalise(PesquisaRelatorioSiacolDto pesquisa) {

		pesquisa.setAssuntos(assuntoConverter.toListDtoSiacol(assuntoDao.getAll()));
		List<RelSiacol09Dto> relatorio = new ArrayList<RelSiacol09Dto>();
		
		if (pesquisa.temPerfilAnalista()) {
			relatorio = populaRel09SemAssuntoSiacol(pesquisa);
		}
		relatorio = populaRel09ComAssuntoSiacol(pesquisa);			
		
		return relatorio;
	}
	
	private List<RelSiacol09Dto> populaRel09SemAssuntoSiacol(PesquisaRelatorioSiacolDto pesquisa) {
		List<RelSiacol09Dto> listaRelatorio = new ArrayList<RelSiacol09Dto>();
		List<Assunto> listaAssuntos = assuntoCorporativoDao.getAssuntoSiacol();
		String[] listaStatus = new String[]{"AGUARDANDO_RECEBIMENTO", "ANALISE", "DISTRIBUICAO_ANALISTA"};
				
		for (Assunto assunto : listaAssuntos) {
			
			RelSiacol09Dto linhaAssunto = new RelSiacol09Dto();
			linhaAssunto.setAssunto(assunto.getId() + " - " + assunto.getDescricao());
			
			List<StatusQtdDto> colunasStatus = new ArrayList<StatusQtdDto>();
			
			for (String nomeStatus : listaStatus) {
				StatusQtdDto status = new StatusQtdDto();
				status.setStatus(nomeStatus);
				status.setTotal(dao.qtdSemAssuntoSiacol(pesquisa, assunto.getId(), nomeStatus));
				colunasStatus.add(status);
			}
			
			linhaAssunto.setStatus(colunasStatus);
				
			if (linhaAssunto.getStatus().get(0).getTotal() != 0 || linhaAssunto.getStatus().get(1).getTotal() != 0 || linhaAssunto.getStatus().get(2).getTotal() != 0) {
				listaRelatorio.add(linhaAssunto);
			}
		}
		
		return listaRelatorio;
	}
	
	private List<RelSiacol09Dto> populaRel09PerfilAnalistaComAssunto(PesquisaRelatorioSiacolDto pesquisa) {
		List<RelSiacol09Dto> listaRelatorio = new ArrayList<RelSiacol09Dto>();
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		
		String[] listaStatus = new String[]{"AGUARDANDO_RECEBIMENTO", "ANALISE", "DISTRIBUICAO_ANALISTA", "PENDENTE_VINCULACAO", "VINCULADO", "DEVOLUCAO", "ANALISE_PROVISORIO_NEGADO", "A_RECEBER_AD_REFERENDUM", "ANALISE_AD_REFERENDUM", "ANALISE_CUMPRIMENTO_OFICIO", "DISTRIBUICAO_COORD_COAC", "PROTOCOLO_PAUSADO"};
		
		for (AssuntoDto assunto : assuntos) {
			RelSiacol09Dto linhaAssunto = new RelSiacol09Dto();
			
			linhaAssunto.setAssunto(assunto.getId() + " - " + assunto.getNome());
			List<StatusQtdDto> colunasStatus = new ArrayList<StatusQtdDto>();
			
			for (String nomeStatus : listaStatus) {
				StatusQtdDto status = new StatusQtdDto();
				status.setStatus(StringUtil.removePontuacao(nomeStatus));
				status.setTotal(dao.qtdProtocolosPorAssuntoStatusEDepartamento(pesquisa, assunto.getId(), nomeStatus));
				colunasStatus.add(status);
			}
		
			linhaAssunto.setStatus(colunasStatus);
			listaRelatorio.add(linhaAssunto);
		 
	    }
		return listaRelatorio;
	}
	
	private List<RelSiacol09Dto> populaRel09PerfilConselheiroComAssunto(PesquisaRelatorioSiacolDto pesquisa) {
		
		List<RelSiacol09Dto> listaRelatorio = new ArrayList<RelSiacol09Dto>();
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		String[] listaStatus = new String[]{"DISTRIBUICAO","PEDIDO_DE_VISTAS","ANALISE_VISTAS", "ANALISE_VISTAS_VOTADO", "ANALISE_VISTAS_A_PAUTAR", "ANALISE_CONSELHEIRO_AD_REFERENDUM"};
		
		for (AssuntoDto assunto : assuntos) {
			RelSiacol09Dto linhaAssunto = new RelSiacol09Dto();
			
			linhaAssunto.setAssunto(assunto.getId() + " - " +  assunto.getNome());
			List<StatusQtdDto> colunasStatus = new ArrayList<StatusQtdDto>();

			for (String nomeStatus : listaStatus) {
				StatusQtdDto status = new StatusQtdDto();
				status.setStatus(StringUtil.removePontuacao(nomeStatus));
				status.setTotal(dao.qtdProtocolosPorAssuntoStatusEDepartamento(pesquisa, assunto.getId(), nomeStatus));
				colunasStatus.add(status);
			}
			
			linhaAssunto.setStatus(colunasStatus);
			listaRelatorio.add(linhaAssunto);
		}
		return listaRelatorio;
	}
	
	
	private List<RelSiacol09Dto> populaRel09ComAssuntoSiacol(PesquisaRelatorioSiacolDto pesquisa) {
		
		List<RelSiacol09Dto> listaRelatorio = new ArrayList<RelSiacol09Dto>();
		
		if (pesquisa.temPerfilAnalista()) {
			listaRelatorio = populaRel09PerfilAnalistaComAssunto(pesquisa);
		}
		 
		if (pesquisa.temPerfilConselheiro()) {
			listaRelatorio = populaRel09PerfilConselheiroComAssunto(pesquisa);
		}
		 
		return listaRelatorio;
	}

}
