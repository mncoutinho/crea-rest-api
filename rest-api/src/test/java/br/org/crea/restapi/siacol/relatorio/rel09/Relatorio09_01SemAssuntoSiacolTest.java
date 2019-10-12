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
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;

public class Relatorio09_01SemAssuntoSiacolTest {

	static RelatorioSiacol09Dao dao;
	static AssuntoDao assuntoCorporativoDao;
	static AssuntoSiacolDao assuntoDao;
	static AssuntoConverter assuntoConverter;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
	    dao = new RelatorioSiacol09Dao();
	    assuntoDao = new AssuntoSiacolDao();
	    assuntoCorporativoDao = new AssuntoDao();
	    assuntoConverter = new AssuntoConverter();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
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
		responsavel.setId(1983103508L);
		responsavel.setPerfil("siacolanalista");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);

		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1205L);
		departamento.setNome("CAMARA CIVIL");
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);
		
		return pesquisa;
	}
	
	@Test
	public void relatoriosTest() {
		
		try {
//			RelSiacol09Dto relatorio = relacaoDetalhadaPorTipoPendenteDeAnalise(populaPesquisa());
						
//			relatorio.getSemAssuntoSiacol().forEach(linhaAssunto -> {
//				System.out.println(linhaAssunto.getAssunto());
//				linhaAssunto.getStatus().forEach(colunaStatus -> {
//					System.out.println(colunaStatus.getStatus() + " | " + colunaStatus.getTotal());
//				});
//			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
//	public RelSiacol09Dto relacaoDetalhadaPorTipoPendenteDeAnalise(PesquisaRelatorioSiacolDto pesquisa) {
//
//		pesquisa.setAssuntos(assuntoConverter.toListDtoSiacol(assuntoDao.getAll()));
//		RelSiacol09Dto relatorio = new RelSiacol09Dto();
//		
//		if (pesquisa.temPerfilAnalista()) {
//			relatorio.setSemAssuntoSiacol(populaRel09SemAssuntoSiacol(pesquisa));
//		}
////		relatorio.setComAssuntoSiacol(populaRel09ComAssuntoSiacol(pesquisa));			
//		
//		return relatorio;
//	}
//	private List<RelSiacol09AssuntoDto> populaRel09SemAssuntoSiacol(PesquisaRelatorioSiacolDto pesquisa) {
//		List<RelSiacol09AssuntoDto> listaRelatorio = new ArrayList<RelSiacol09AssuntoDto>();
//		List<Assunto> listaAssuntos = assuntoCorporativoDao.getAssuntoSiacol();
//		String[] listaStatus = new String[]{"AGUARDANDO_RECEBIMENTO", "ANALISE", "DISTRIBUICAO_ANALISTA"};
//				
//		for (Assunto assunto : listaAssuntos) {
//			
//			RelSiacol09AssuntoDto linhaAssunto = new RelSiacol09AssuntoDto();
//			linhaAssunto.setAssunto(assunto.getId() + " - " + assunto.getDescricao());
//			
//			List<StatusQtdDto> colunasStatus = new ArrayList<StatusQtdDto>();
//			
//			for (String nomeStatus : listaStatus) {
//				StatusQtdDto status = new StatusQtdDto();
//				status.setStatus(nomeStatus);
//				status.setTotal(dao.rel09QtdSemAssuntoSiacol(pesquisa, assunto.getId(), nomeStatus));
//				colunasStatus.add(status);
//			}
//			
//			linhaAssunto.setStatus(colunasStatus);
//				
//			if (linhaAssunto.getStatus().get(0).getTotal() != 0 || linhaAssunto.getStatus().get(1).getTotal() != 0 || linhaAssunto.getStatus().get(2).getTotal() != 0) {
//				listaRelatorio.add(linhaAssunto);
//			}
//		}
//		
//		return listaRelatorio;
//	}
	
}
