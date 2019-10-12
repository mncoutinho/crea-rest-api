package br.org.crea.restapi.siacol.relatorio.rel07;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol07Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07RecebimentoDto;
import br.org.crea.commons.util.DateUtils;

public class Rel07_00QuerysTest {

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

		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1208L);
		departamento.setNome("CAMARA QUIMICA");
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);
		
		pesquisa.setAno("2019");
		List<String> meses = new ArrayList<String>();
		meses.add("01");
		pesquisa.setMeses(meses);
		
		return pesquisa;
	}
	
	///////////************* RECEBIDOS ANALISTA ***********************//////
	
//	@Test
	public void distribuicaoPorOutroUsuarioTest() {
		
		try {
			PesquisaRelatorioSiacolDto pesquisa = populaPesquisa();
			pesquisa.setAno("2018");
			List<String> meses = new ArrayList<String>();
			meses.add("12");
			pesquisa.setMeses(meses);
			Long idAssuntoSiacol = 48L;
			List<ProtocolosRel07RecebimentoDto> listaRelatorio = dao.getDistribuicaoPorOutroUsuario(pesquisa, idAssuntoSiacol);
			
			System.out.println("Id Aud | No. Protocolo | Data");
			listaRelatorio.forEach(linha -> {
				System.out.println(linha.getIdAuditoria() + " | " + linha.getNumeroProtocolo() + " | " + linha.getData());
			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
//	@Test
	public void recebimentoSiacolTest() {
		
		try {
			Long idAssuntoSiacol = 47L;
			List<ProtocolosRel07RecebimentoDto> listaRelatorio = dao.getRecebimentoSiacol(populaPesquisa(), idAssuntoSiacol);
			
			System.out.println("Id Aud | No. Protocolo | Data");
			listaRelatorio.forEach(linha -> {
				System.out.println(linha.getIdAuditoria() + " | " + linha.getNumeroProtocolo() + " | " + DateUtils.format(linha.getData(), DateUtils.DD_MM_YYYY_HH_MM_SS));
			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
		
	


}
