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

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol13Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol13Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio13ProtocolosAssuntosSiacolECorporativoPorDepartamentoTest {

	static RelatorioSiacol13Dao dao;
	private static EntityManager em = null;
	private SiacolRelXlsBuilder builder;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		builder = new SiacolRelXlsBuilder();
		dao = new RelatorioSiacol13Dao();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			List<RelSiacol13Dto> listaRelatorio = dao.relatorioDeProtocolosAssuntosSiacolECorporativoPorDepartamento(populaPesquisa());
			
			System.out.println("Número Protocolo | Cod Ass Siacol | Ass Siacol | Cod Ass Corp | Ass Corp | Matr Criador | Nome Criador | Dept Criacao");
			
			listaRelatorio.forEach(item -> {
				System.out.println(item.getNumeroProtocolo() + " | " + item.getCodigoAssuntoSiacol() + " | " + item.getDescricaoAssuntoSiacol() 
				+ " | " + item.getCodigoAssuntoCorporativo() + " | " + item.getDescricaoAssuntoCorporativo() + " | " + item.getMatriculaCriador()
				+ " | " + item.getNomeCriador() + " | " + item.getDepartamentoCriacao());
			});
			
			File file = new File("/opt/temp/rel13.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel13(listaRelatorio));
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
		departamento.setNome("CAMARA CIVIL");
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);

		List<AssuntoDto> assuntos = new ArrayList<AssuntoDto>();
		AssuntoDto assunto = new AssuntoDto();
		assunto.setId(13L);
		assunto.setNome("X");
		assuntos.add(assunto);
		
		assunto = new AssuntoDto();
		assunto.setId(47L);
		assunto.setNome("Y");
		assuntos.add(assunto);
		
		assunto = new AssuntoDto();
		assunto.setId(51L);
		assunto.setNome("Z");
		assuntos.add(assunto);
		pesquisa.setAssuntos(assuntos);
		
		pesquisa.setAno("2018");
		
		List<String> meses = new ArrayList<String>();
		meses.add("11");
		pesquisa.setMeses(meses);
		
		pesquisa.setTodosDepartamentos(false);
		pesquisa.setTodosAssuntos(false);
		
		return pesquisa;
	}
}
