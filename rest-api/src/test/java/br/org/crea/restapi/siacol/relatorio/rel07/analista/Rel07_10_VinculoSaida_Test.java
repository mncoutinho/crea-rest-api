package br.org.crea.restapi.siacol.relatorio.rel07.analista;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol07Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07RecebimentoDto;

public class Rel07_10_VinculoSaida_Test {

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
		responsavel.setId(2000105584L);
		responsavel.setPerfil("siacolanalista");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);

		pesquisa.setAno("2018");
		List<String> meses = new ArrayList<String>();
		meses.add("12");
		pesquisa.setMeses(meses);
		
		return pesquisa;
	}
	
//	@Test
	public void saidaVinculo() {
		
		try {
			ProtocolosRel07RecebimentoDto saidaVinculo = null;
			
			Long idAssuntoSiacol = 47L;
			List<ProtocolosRel07RecebimentoDto> listaEntradaVinculo = dao.getEntradaVinculoAnalista(populaPesquisa(), idAssuntoSiacol);
			
			for (ProtocolosRel07RecebimentoDto entradaVinculo : listaEntradaVinculo) {
				saidaVinculo = dao.getSaidaVinculoAnalista(populaPesquisa(), entradaVinculo.getIdAuditoria());
				if (saidaVinculo != null) {
					System.out.println(saidaVinculo.getIdAuditoria() + " | " + saidaVinculo.getNumeroProtocolo() + " | " + saidaVinculo.getData());
				}
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	

}
