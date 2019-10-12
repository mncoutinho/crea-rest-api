package br.org.crea.restapi.commons;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.org.crea.atendimento.dao.AgendamentoDao;

public class EnderecoTest {
	private static EntityManager em = null;
	
	@BeforeClass
	public static void inicio() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		Assert.assertTrue("entity manager iniciado", em != null);
	}
	
	@AfterClass
	public static void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	
	@Test
	public void deveTrazerEnderecoResidencial() {
		
		AgendamentoDao dao = new AgendamentoDao();
		
		dao.setEntityManager(em);
		
		
		
		
		
//		dao.apagarAgendadosEdicao();

		
		
		
		
//		dto.setDataInicio("2018-03-01T03:00:00.000Z");
//		dto.setDataFim(2018-03-01T03:00:00.000Z);
		
		


			
	}


}
