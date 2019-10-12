package br.org.crea.restapi.cadastro;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.PremioTCTDao;
import br.org.crea.commons.models.cadastro.PremioTCT;

public class PremioTCTRelatorio01Test {
	static PremioTCTDao dao;
	private static EntityManager em = null;
	
	@Before
	public  void inicio() {
		
	    dao = new PremioTCTDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		Assert.assertTrue("entity manager iniciado", em != null);
	}
	
	@After
	public void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	
	@Test
	public void PremioTest() {
		
		try {
			dao.setEntityManager(em);
			
			PremioTCT premio = new PremioTCT();
			
			premio.setAno(new Long(2018));
			
			em.getTransaction().begin();
			dao.create(premio);
			em.getTransaction().commit();
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
			
	}


}
