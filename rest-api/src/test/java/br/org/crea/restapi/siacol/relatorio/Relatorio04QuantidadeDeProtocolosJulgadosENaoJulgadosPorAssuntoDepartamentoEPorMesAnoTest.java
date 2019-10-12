package br.org.crea.restapi.siacol.relatorio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol04Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDepartamentosSiacol04Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol04Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio04QuantidadeDeProtocolosJulgadosENaoJulgadosPorAssuntoDepartamentoEPorMesAnoTest {
	
	static RelatorioSiacol04Dao dao;
	static DepartamentoDao departamentoDao;
	static DepartamentoConverter departamentoConverter;
	static AssuntoConverter assuntoConverter;
	static AssuntoSiacolDao assuntoDao;
	private static EntityManager em = null;
	private SiacolRelXlsBuilder builder;
	
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		builder = new SiacolRelXlsBuilder();
		dao = new RelatorioSiacol04Dao();
		departamentoDao = new DepartamentoDao();
		departamentoConverter = new DepartamentoConverter();
		assuntoConverter = new AssuntoConverter();
		assuntoDao = new AssuntoSiacolDao();
		dao.setEntityManager(em);
		departamentoDao.setEntityManager(em);
		assuntoDao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
//	@Test
	public void qtdJulgadosPorDepartamentoEAssunto() {
		DepartamentoDto departamento = departamentoConverter.toDto(departamentoDao.getBy(1208));
		Long idAssunto = 51L;
//		System.out.println("Qtd Julgado por Dept: " + dao.rel04JulgadosPorDepartamentoEPorAssunto(populaPesquisa(), idDepartamento, idAssunto));
		System.out.println("Qtd Julgado por Dept: " + dao.getJulgadosPorDepartamentoEPorAssunto(populaPesquisa(), departamento, idAssunto));
	}
	
//	@Test
	public void qtdNaoJulgadosPorDepartamentoEAssunto1() {
		Long idDepartamento = 1208L;
		DepartamentoDto departamento = departamentoConverter.toDto(departamentoDao.getBy(1208));
		Long idAssunto = 52L;
//		System.out.println("Qtd não julgado por Dept: " + dao.rel04NaoJulgadosPorDepartamentoEPorAssunto(populaPesquisa(), idDepartamento, idAssunto));
		System.out.println("Qtd não julgado por Dept: " + dao.getNaoJulgadosPorDepartamentoEPorAssunto(populaPesquisa(), departamento, idAssunto));
	}
	
//	@Test
	public void qtdNaoJulgadosPorDepartamentoEAssunto2() {
		Long idDepartamento = 1208L;
		Long idAssunto = 51L;
//		System.out.println("Qtd: " + dao.rel04NaoJulgadosPorDepartamentoEPorAssunto2(populaPesquisa(), idDepartamento, idAssunto));
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			PesquisaRelatorioSiacolDto pesquisa = populaPesquisa();
			List<RelSiacol04Dto> listaRelatorio = quantidadeDeProtocolosJulgadosENaoJulgadosPorAssuntoEDepartamentosEPorMesAno(pesquisa);
			
			listaRelatorio.forEach(linhaAssunto -> {
				System.out.println(linhaAssunto.getAssunto());
				linhaAssunto.getDepartamentos().forEach(departamento -> {
					if (pesquisa.getClassificacao().get(0).getTipo().equals("MESCLADO") && !departamento.getQtd().equals("0/0")) {
						System.out.println("  " + departamento.getNome() + " | " + departamento.getQtd());
					} else if (!pesquisa.getClassificacao().get(0).getTipo().equals("MESCLADO") && !departamento.getQtd().equals("0")) {
						System.out.println("  " + departamento.getNome() + " | " + departamento.getQtd());
					}
					
				});
			});
			
			File file = new File("/opt/temp/rel04.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel04(listaRelatorio));
			bos.close();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
		
		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1208L);
		departamento.setNome("CAMARA QUIMICA");
		departamentos.add(departamento);
		
		pesquisa.setDepartamentos(departamentos);
		
		List<AssuntoDto> assuntos = new ArrayList<AssuntoDto>();
		
		AssuntoDto assunto = new AssuntoDto();
		assunto.setId(13L);
		assunto.setNome("X");
		assuntos.add(assunto);
		pesquisa.setAssuntos(assuntos);
		
		pesquisa.setAno("2018");
		
		List<GenericDto> classificacoes = new ArrayList<GenericDto>();
		GenericDto classificacao = new GenericDto();
		classificacao.setTipo("MESCLADO");
		classificacoes.add(classificacao);
		pesquisa.setClassificacao(classificacoes);
		
		//pesquisa.setStatusJulgado(classificacoes);
		
		pesquisa.setTodosAssuntos(true);
		pesquisa.setTodosDepartamentos(false);
		
		return pesquisa;
	}
	
	public List<RelSiacol04Dto> quantidadeDeProtocolosJulgadosENaoJulgadosPorAssuntoEDepartamentosEPorMesAno(PesquisaRelatorioSiacolDto pesquisa) {

		List<DepartamentoDto> departamentos = pesquisa.getDepartamentos();
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		
		if (pesquisa.isTodosDepartamentos()) {
			departamentos = departamentoConverter.toListDto(departamentoDao.getAllDepartamentos("SIACOL")); 
		}
		
		if (pesquisa.isTodosAssuntos()) {
			assuntos = assuntoConverter.toListDtoSiacol(assuntoDao.getAll());
		}
		
		List<RelSiacol04Dto> listaRelatorio = new ArrayList<RelSiacol04Dto>();
		AssuntoDto semAssunto = new AssuntoDto();
		semAssunto.setId(0L);
		semAssunto.setNome("SEM ASSUNTO SIACOL");
		assuntos.add(0, semAssunto);
		
//		for (AssuntoDto assunto : assuntos) {
			RelSiacol04Dto linhaAssunto = new RelSiacol04Dto();
//			linhaAssunto.setAssunto(assunto.getNome());
			
			List<RelDepartamentosSiacol04Dto> valoresDaLinhaAssunto = new ArrayList<RelDepartamentosSiacol04Dto>();
//			for (DepartamentoDto departamento : departamentos) {
//				RelDepartamentosSiacol04Dto valorDaCelulaDepartamentoxAssunto = new RelDepartamentosSiacol04Dto();
//				valorDaCelulaDepartamentoxAssunto.setNome(departamento.getNome());
//				String total = "";
//				if (pesquisa.filtroEhJulgados()) {
//					total = String.valueOf(dao.rel04JulgadosPorDepartamentoEPorAssunto(pesquisa, departamento.getId(), assunto.getId()));
//				} else if (pesquisa.filtroEhNaoJulgados()) {
//					total = String.valueOf(dao.rel04NaoJulgadosPorDepartamentoEPorAssunto(pesquisa, departamento.getId(), assunto.getId()));
//				} else if (pesquisa.filtroEhMesclado()) {
			for (DepartamentoDto departamento : departamentos) {
				RelDepartamentosSiacol04Dto valorDaCelulaDepartamentoxAssunto = new RelDepartamentosSiacol04Dto();
				valorDaCelulaDepartamentoxAssunto.setNome(departamento.getNome());
				String total = "";
				if (pesquisa.filtroEhJulgados()) {
	//				total = String.valueOf(dao.rel04JulgadosPorDepartamentoEPorAssunto(pesquisa, departamento, assunto.getId()));
				} else if (pesquisa.filtroEhNaoJulgados()) {
//					total = String.valueOf(dao.rel04NaoJulgadosPorDepartamentoEPorAssunto(pesquisa, departamento, assunto.getId()));
				} else if (pesquisa.filtroEhMesclado()) {
//					int qtdJulgado = dao.rel04JulgadosPorDepartamentoEPorAssunto(pesquisa, departamento.getId(), assunto.getId());
//					int qtdNaoJulgado1 = dao.rel04NaoJulgadosPorDepartamentoEPorAssunto(pesquisa, departamento.getId(), assunto.getId());
//					int qtdNaoJulgado2 = dao.rel04NaoJulgadosPorDepartamentoEPorAssunto2(pesquisa, departamento.getId(), assunto.getId());
//					total = String.valueOf(qtdJulgado) + "/" + String.valueOf(qtdNaoJulgado1 + qtdNaoJulgado2);
				}
				
//				valorDaCelulaDepartamentoxAssunto.setQtd(total);
				
//				valoresDaLinhaAssunto.add(valorDaCelulaDepartamentoxAssunto);
//			}
//			linhaAssunto.setDepartamentos(valoresDaLinhaAssunto);
//			listaRelatorio.add(linhaAssunto);
		}
		
		return listaRelatorio;
	}
		
}
