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

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol12Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol12Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio12SomatorioDoTotalDeDecisoesPorDepartamentoTest {

	static RelatorioSiacol12Dao dao;
	private static EntityManager em = null;
	private SiacolRelXlsBuilder builder;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		builder = new SiacolRelXlsBuilder();
		dao = new RelatorioSiacol12Dao();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	private PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
		
		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1205L);
		departamento.setNome("CAMARA CIVIL");
		departamento.setSigla("CEEC");
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);
		
		pesquisa.setAno("2018");
		
		return pesquisa;
	}
	
//	@Test
	public void qtdDecisoesFavoraveisTest() {
		System.out.println("Qtd Decisões Favoráveis: " + dao.qtdDecisoes(populaPesquisa(), true));
	}
	
//	@Test
	public void qtdDecisoesDesavoraveisTest() {
		System.out.println("Qtd Decisões Desfavoráveis: " + dao.qtdDecisoes(populaPesquisa(), false));
	}
	
//	@Test
	public void qtdDecisoesHomologacaoTest() {
		System.out.println("Qtd Decisões Homologação: " + dao.qtdDecisoesHomologacao(populaPesquisa()));
	}
	
//	@Test
	public void qtdDecisoesAssuntoTest() {
		System.out.println("Qtd Decisões Assunto: " + dao.qtdDecisoesAssunto(populaPesquisa()));
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			List<RelSiacol12Dto> listRelatorio = dao.somatorioDoTotalDeDecisoesPorDepartamento(populaPesquisa());
			
			for (RelSiacol12Dto relatorio : listRelatorio) {
				System.out.println("Departamento | Favoraveis | Desfavoraveis | Homologacao | Assunto ");
				System.out.println(relatorio.getDepartamento() + " | " + relatorio.getQtdDecisoesFavoraveis() + 
						" | " + relatorio.getQtdDecisoesDesfavoraveis() + " | " + relatorio.getQtdDecisoesHomologacao() +
						" | " + relatorio.getQtdDecisoesAssunto());
			}
			File file = new File("/opt/temp/rel12.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel12(listRelatorio));
			bos.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
