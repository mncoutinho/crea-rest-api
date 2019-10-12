package br.org.crea.restapi.siacol.relatorio.rel09;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol09Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;

public class Rel09_00QuerysTest {

	static RelatorioSiacol09Dao dao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioSiacol09Dao();
		dao.setEntityManager(em);
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
		
		pesquisa.setAno("2018");
		
		List<String> meses = new ArrayList<String>();
		meses.add("11");
		pesquisa.setMeses(meses);
		
		return pesquisa;
	}
	
	@Test
	public void qtdSemAssuntoSiacolTest() {
		Long idAssuntoCorporativo = 2001l;
		String status = "AGUARDANDO_RECEBIMENTO";
		System.out.println("Qtd Sem Assunto Siacol: " + dao.qtdSemAssuntoSiacol(populaPesquisa(), idAssuntoCorporativo, status));
	}
	
	@Test
	public void qtdProtocolosPorAssuntoStatusEDepartamentoTeste() {
		Long idAssuntoSiacol = 51l;
		String status = "AGUARDANDO_RECEBIMENTO";
		System.out.println("Qtd Com Assunto Siacol: " + dao.qtdProtocolosPorAssuntoStatusEDepartamento(populaPesquisa(), idAssuntoSiacol, status));
	}

}
