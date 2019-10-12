package br.org.crea.restapi.siacol.relatorio.rel10;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol10Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol10Dto;

public class Relatorio10GetEntradaTest {
	
	private RelatorioSiacol10Dao dao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioSiacol10Dao();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void getEntrada() {
		List<RelDetalhadoSiacol10Dto> listaEntrada = dao.getEntradaAnalistaDistribuicaoOuTramitacao(populaPesquisa(), 48L, "CAMARA");
		
		listaEntrada.forEach(entrada -> {
			System.out.println(entrada.getIdAuditoria() + " | " + entrada.getData());
		});
	}
	
	public PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();

		List<PessoaDto> responsaveis = new ArrayList<PessoaDto>();
		PessoaDto responsavel = new PessoaDto();
		responsavel.setId(2000105584L);
		responsavel.setPerfil("siacolanalista");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);

		pesquisa.setAno("2019");
		List<String> meses = new ArrayList<String>();
		meses.add("01");
		pesquisa.setMeses(meses);
		
		pesquisa.setTodosAssuntos(false);
		
		return pesquisa;
	}

}
