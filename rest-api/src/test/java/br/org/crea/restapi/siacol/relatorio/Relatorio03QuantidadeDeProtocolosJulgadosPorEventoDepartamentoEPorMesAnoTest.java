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

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol03Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol03Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio03QuantidadeDeProtocolosJulgadosPorEventoDepartamentoEPorMesAnoTest {

	static RelatorioSiacol03Dao dao;
	private static EntityManager em = null;
	private SiacolRelXlsBuilder builder;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioSiacol03Dao();
		builder = new SiacolRelXlsBuilder();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void qtdTest() {
		Long idDepartamento = 1208L;
		Long idAssunto = 51L;
		List<String> lista = dao.getProtocolosPorDepartamentoEPorEvento(populaPesquisa(), idDepartamento, idAssunto);
		System.out.println("Qtd: " + lista.size());
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			List<RelSiacol03Dto> listaRelatorio = dao.quantidadeDeProtocolosJulgadosPorEventoDepartamentosEPorMesAno(populaPesquisa());
						
			listaRelatorio.forEach(linhaEvento -> {
				System.out.println(linhaEvento.getEvento());
				linhaEvento.getDepartamentos().forEach(valoresLinhaEvento -> {
					System.out.println(valoresLinhaEvento.getNome() + " | " + valoresLinhaEvento.getQtd());
				});
			});
			
			File file = new File("/opt/temp/rel03.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel03(listaRelatorio));
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
		departamentos.add(departamento);
		
		pesquisa.setDepartamentos(departamentos);
		pesquisa.setAno("2018");
		
		return pesquisa;
	}
}
