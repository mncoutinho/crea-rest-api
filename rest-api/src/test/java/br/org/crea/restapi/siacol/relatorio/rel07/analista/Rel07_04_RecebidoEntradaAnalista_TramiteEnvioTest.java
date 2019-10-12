package br.org.crea.restapi.siacol.relatorio.rel07.analista;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol07Dao;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07RecebimentoDto;

public class Rel07_04_RecebidoEntradaAnalista_TramiteEnvioTest {

	static RelatorioSiacol07Dao dao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioSiacol07Dao();
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
		responsavel.setId(2001107550L);
		responsavel.setPerfil("siacolanalista");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);

		pesquisa.setAno("2018");
		List<String> meses = new ArrayList<String>();
		meses.add("08"); 
		meses.add("09");
		meses.add("10");
		meses.add("11");
		meses.add("12");
		pesquisa.setMeses(meses);
		
		return pesquisa;
	}
	
	@Test
	public void tramiteEnvioTest() {
		
		try {
			List<ProtocolosRel07RecebimentoDto> listaEntrada = new ArrayList<ProtocolosRel07RecebimentoDto>();
			ProtocolosRel07RecebimentoDto relatorio = null;
			
			Long idAssuntoSiacol = 47L;
			List<ProtocolosRel07RecebimentoDto> listaRelatorio = dao.getRecebimentoSiacol(populaPesquisa(), idAssuntoSiacol);
			
			for (ProtocolosRel07RecebimentoDto recebimento : listaRelatorio) {
				relatorio = dao.getTramiteEnvioAnalista(populaPesquisa(), recebimento.getNumeroProtocolo(), recebimento.getIdAuditoria());
				if (relatorio != null) {
					listaEntrada.add(relatorio);
				}
			}
			listaEntrada.forEach(entrada -> {
				System.out.println(entrada.getIdAuditoria() + " | " + entrada.getNumeroProtocolo() + " | " + entrada.getData());
			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
