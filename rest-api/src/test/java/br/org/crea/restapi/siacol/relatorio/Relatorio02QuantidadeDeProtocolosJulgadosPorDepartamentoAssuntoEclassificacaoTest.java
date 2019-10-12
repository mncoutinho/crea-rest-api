package br.org.crea.restapi.siacol.relatorio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
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
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol02Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol02Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio02QuantidadeDeProtocolosJulgadosPorDepartamentoAssuntoEclassificacaoTest {

	static RelatorioSiacol02Dao dao;
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
		dao = new RelatorioSiacol02Dao();
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
	public void qtdConcedidoRegistroProvisorioTest() {
		Long idAssuntoSiacol = 51L;
		List<Long> idsDepartamentos = new ArrayList<Long>();
		Long idDepartamento = 1205L;
		idsDepartamentos.add(idDepartamento);
		
		RelSiacol02Dto linhaAssunto = new RelSiacol02Dto();
		dao.qtdConcedido(linhaAssunto, populaPesquisa(), idAssuntoSiacol, idsDepartamentos, new Long(1110));
		
		linhaAssunto.getProtocolosConcedidoProvisorio().forEach(relatorio -> {
			System.out.println("Qtd: " + relatorio.getQtd() + " Protocolo: " + relatorio.getProtocolo());
		});
		
		System.out.println("Qtd Registro Provisorio Concedido: " + linhaAssunto.getQtdConcedidoRegistroProvisorio());
	}
	
	@Test
	public void qtdAprovadoRegistroProvisorioTest() {
		Long idAssuntoSiacol = 51L;
		List<Long> idsDepartamentos = new ArrayList<Long>();
		Long idDepartamento = 1205L;
		idsDepartamentos.add(idDepartamento);
		
		RelSiacol02Dto linhaAssunto = new RelSiacol02Dto();
		dao.qtdAprovado(linhaAssunto, populaPesquisa(), idAssuntoSiacol, idsDepartamentos, new Long(1110));
		
		linhaAssunto.getProtocolosConcedidoProvisorio().forEach(relatorio -> {
			System.out.println("Qtd: " + relatorio.getQtd() + " Protocolo: " + relatorio.getProtocolo());
		});
		
		System.out.println("Qtd Registro Provisorio Aprovado: " + linhaAssunto.getQtdConcedidoRegistroProvisorio());
	}
	
//	@Test
	public void qtdConcedidoAdReferendumTest() {
		Long idAssuntoSiacol = 51L;
		List<Long> idsDepartamentos = new ArrayList<Long>();
		Long idDepartamento = 1205L;
		idsDepartamentos.add(idDepartamento);
		
		RelSiacol02Dto linhaAssunto = new RelSiacol02Dto();
		dao.qtdConcedido(linhaAssunto, populaPesquisa(), idAssuntoSiacol, idsDepartamentos, new Long(1112));
		
		linhaAssunto.getProtocolosConcedidoProvisorio().forEach(relatorio -> {
			System.out.println("Qtd: " + relatorio.getQtd() + " Protocolo: " + relatorio.getProtocolo());
		});
		
		System.out.println("Qtd Ad Referendum Concedido: " + linhaAssunto.getQtdConcedidoRegistroProvisorio());
	}
	
//	@Test
	public void qtdAprovadoAdReferendumTest() {
		Long idAssuntoSiacol = 51L;
		List<Long> idsDepartamentos = new ArrayList<Long>();
		Long idDepartamento = 1205L;
		idsDepartamentos.add(idDepartamento);
		
		RelSiacol02Dto linhaAssunto = new RelSiacol02Dto();
		dao.qtdAprovado(linhaAssunto, populaPesquisa(), idAssuntoSiacol, idsDepartamentos, new Long(1112));
		
		linhaAssunto.getProtocolosConcedidoProvisorio().forEach(relatorio -> {
			System.out.println("Qtd: " + relatorio.getQtd() + " Protocolo: " + relatorio.getProtocolo());
		});
		
		System.out.println("Qtd Ad Referendum Aprovado: " + linhaAssunto.getQtdConcedidoRegistroProvisorio());
	}
	
//	@Test
	public void qtdReuniaoVirtualTest() {
		Long idAssuntoSiacol = 51L;
		List<Long> idsDepartamentos = new ArrayList<Long>();
		Long idDepartamento = 1205L;
		idsDepartamentos.add(idDepartamento);
		
		RelSiacol02Dto linhaAssunto = new RelSiacol02Dto();
		dao.qtdReuniao(linhaAssunto, populaPesquisa(), idAssuntoSiacol, idsDepartamentos, true);
		
		linhaAssunto.getProtocolosConcedidoProvisorio().forEach(relatorio -> {
			System.out.println("Qtd: " + relatorio.getQtd() + " Protocolo: " + relatorio.getProtocolo());
		});
		
		System.out.println("Qtd Reunião Virtual: " + linhaAssunto.getQtdConcedidoRegistroProvisorio());
	}
	
//	@Test
	public void qtdReuniaoPresencialTest() {
		Long idAssuntoSiacol = 51L;
		List<Long> idsDepartamentos = new ArrayList<Long>();
		Long idDepartamento = 1205L;
		idsDepartamentos.add(idDepartamento);
		
		RelSiacol02Dto linhaAssunto = new RelSiacol02Dto();
		dao.qtdReuniao(linhaAssunto, populaPesquisa(), idAssuntoSiacol, idsDepartamentos, false);
		
		linhaAssunto.getProtocolosConcedidoProvisorio().forEach(relatorio -> {
			System.out.println("Qtd: " + relatorio.getQtd() + " Protocolo: " + relatorio.getProtocolo());
		});
		
		System.out.println("Qtd Reunião Presencial: " + linhaAssunto.getQtdConcedidoRegistroProvisorio());
	}
	
//	@Test
	public void relatorioTest() {
		
		try {
			List<RelSiacol02Dto> listaRelatorio = quantidadeDeProtocolosJulgadosPorDepartamentoAssuntoEClassificacao(populaPesquisa());
			
			System.out.println("ASSUNTO | PROV CONCEDIDO | PROV APROVADO | AD REF CONCEDIDO | AD REF APROVADO | REUNIAO VIRTUAL | REUNIAO PRESENCIAL");
			listaRelatorio.forEach(linha -> {
				if (linha.getQtdConcedidoRegistroProvisorio() != 0 || linha.getQtdAprovadoRegistroProvisorio() != 0 || linha.getQtdConcedidoAdReferendum() != 0 ||
				linha.getQtdAprovadoAdReferendum() != 0 || linha.getQtdReuniaoVirtual() != 0 || linha.getQtdReuniaoPresencial() != 0) {
					System.out.println(linha.getAssunto() + " | " + linha.getQtdConcedidoRegistroProvisorio() + " | " + linha.getQtdAprovadoRegistroProvisorio() + " | " + linha.getQtdConcedidoAdReferendum()
					+ " | " + linha.getQtdAprovadoAdReferendum() + " | " + linha.getQtdReuniaoVirtual() + " | " + linha.getQtdReuniaoPresencial());
				}
			});
			
			File file = new File("/opt/temp/rel02.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel02(listaRelatorio));
			bos.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
		
		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1205L);
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);
		
		List<AssuntoDto> assuntos = new ArrayList<AssuntoDto>();
		AssuntoDto assunto = new AssuntoDto();
		assunto.setId(51L);
		assunto.setNome("X");
		assuntos.add(assunto);
	//	assunto = new AssuntoDto();
		//assunto.setId(13L);
	//	assunto.setNome("Y");
	//	assuntos.add(assunto);
		pesquisa.setAssuntos(assuntos);
		
		pesquisa.setAno("2018");
		
		
		List<GenericDto> filtros = new ArrayList<GenericDto>();
		GenericDto filtro = new GenericDto();
		filtro.setNome("Desfavoráveis Reprovados");
		filtro.setTipo("FAVORAVEL_APROVADO");
		filtros.add(filtro);
		filtro = new GenericDto();
		filtro.setNome("Desfavoráveis Reprovados");
		filtro.setTipo("DESFAVORAVEL_APROVADO");
		filtros.add(filtro);
		filtro = new GenericDto();
		filtro.setNome("Desfavoráveis Reprovados");
		filtro.setTipo("FAVORAVEL_REPROVADO");
		filtros.add(filtro);
		filtro = new GenericDto();
		filtro.setNome("Desfavoráveis Reprovados");
		filtro.setTipo("DESFAVORAVEL_REPROVADO");
		filtros.add(filtro);
		pesquisa.setClassificacao(filtros);
		
		pesquisa.setTodosDepartamentos(false);
		pesquisa.setTodosAssuntos(true);
		pesquisa.setTodasClassificacoes(false);
		
		return pesquisa;
	}
	
	public List<RelSiacol02Dto> quantidadeDeProtocolosJulgadosPorDepartamentoAssuntoEClassificacao(PesquisaRelatorioSiacolDto pesquisa) {
		
		List<Long> idsDepartamentos = populaListaIdsDepartamentos(pesquisa.getDepartamentos());
		
		List<RelSiacol02Dto> listaRelatorio = new ArrayList<RelSiacol02Dto>();
		
		if (pesquisa.isTodosAssuntos()) {
			pesquisa.setAssuntos(assuntoConverter.toListDtoSiacol(assuntoDao.getAll()));
		}
		
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		assuntos.sort(Comparator.comparing(AssuntoDto::getId));
		
		for (AssuntoDto assunto : assuntos) {
			RelSiacol02Dto linhaAssunto = new RelSiacol02Dto();
			linhaAssunto.setAssunto(assunto.getId() + " - " + assunto.getNome());
			
			dao.qtdConcedido(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, new Long(1110));
			dao.qtdAprovado(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, new Long(1110));
			dao.qtdConcedido(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, new Long(1112));
			dao.qtdAprovado(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, new Long(1112));
			linhaAssunto.setQtdTotalADeRP(linhaAssunto.getQtdAprovadoRegistroProvisorio() + linhaAssunto.getQtdAprovadoAdReferendum());
			dao.qtdReuniao(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, true);
			dao.qtdReuniao(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, false);
			linhaAssunto.setQtdTotalReunioes(linhaAssunto.getQtdReuniaoPresencial() + linhaAssunto.getQtdReuniaoVirtual());
			listaRelatorio.add(linhaAssunto);
		}
		
		return listaRelatorio;
	}
	
	private List<Long> populaListaIdsDepartamentos(List<DepartamentoDto> departamentos) {
		List<Long> listaIds = new ArrayList<Long>();
		
		for (DepartamentoDto departamento : departamentos) {
			listaIds.add(departamento.getId());
		}
		return listaIds;
	}
}
