package br.org.crea.restapi.siacol.relatorio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol11Dao;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol11Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio11VidaDoProtocoloTest {

	static RelatorioSiacol11Dao dao;
	private static EntityManager em = null;
	private SiacolRelXlsBuilder builder;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		builder = new SiacolRelXlsBuilder();
		dao = new RelatorioSiacol11Dao();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	private PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
		pesquisa.setNumeroProtocolo("2018201564");
		
		return pesquisa;
	}
	
	@Test
	public void relatorioTest() {
		
		try {
			List<RelSiacol11Dto> listaRelatorio = dao.vidaDoProtocolo(populaPesquisa());

			listaRelatorio.forEach(item -> {
				System.out.println(item.getDataFormatada() + " | " + item.getTextoAuditoria());
			});
			
			File file = new File("/opt/temp/rel11.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel11(listaRelatorio));
			bos.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
