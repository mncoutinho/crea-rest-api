package br.org.crea.restapi.siacol;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;

public class ReuniaoSiacolTest {
	
	private static EntityManager em = null;
	
	@BeforeClass
	public static void inicio() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		assertTrue("entity manager iniciado", em != null);
	}
	
	@AfterClass
	public static void fim() {
		em.close();
		assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	@Test
	public void deveTrazerEnderecoResidencial() {
		
		ReuniaoSiacolDao dao = new ReuniaoSiacolDao();
		dao.setEntityManager(em);
		
	
		
	}

	
	
	

}
