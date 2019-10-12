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

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol14Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol14Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio14QuantidadeMensalDeProtocolosJulgadosNasReunioesPresenciaisEVirtuaisPorDepartamentoTest {

	static RelatorioSiacol14Dao dao;
	private static EntityManager em = null;
	private SiacolRelXlsBuilder builder;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		builder = new SiacolRelXlsBuilder();
		dao = new RelatorioSiacol14Dao();
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
	
	//@Test
	public void qtdReuniaoVirtualTest() {
		String mes = "11";
		Long idDepartamento = 1208L;
		System.out.println("Qtd Reuniao Virtual: " +  dao.qtdReuniao(populaPesquisa(), idDepartamento, mes, true));
	}
	
	//@Test
	public void qtdReuniaoPresencialTest() {
		String mes = "10";
		Long idDepartamento = 1205L;
		System.out.println("Qtd Reuniao Presencial: " +  dao.qtdReuniao(populaPesquisa(), idDepartamento, mes, false));
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			List<RelSiacol14Dto> listaRelatorio = dao.quantidadeMensalDeProtocolosJulgadosNasReunioesPresenciaisEVirtuaisPorDepartamento(populaPesquisa());
			
			listaRelatorio.forEach(item -> {
				System.out.println(item.getMes());
				item.getDepartamentos().forEach(dept -> {
					System.out.println(dept.getNome() + " | " + dept.getQtdReuniaoPresencial() + " | " + dept.getQtdReuniaoVirtual());
				});
			});
			
			File file = new File("/opt/temp/rel14.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel14(listaRelatorio));
			bos.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
