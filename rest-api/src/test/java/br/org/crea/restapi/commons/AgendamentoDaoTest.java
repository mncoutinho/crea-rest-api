package br.org.crea.restapi.commons;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.atendimento.dao.AssuntoMobileDao;

public class AgendamentoDaoTest {
	static AssuntoMobileDao dao;
	private static EntityManager em = null;
	
	@Before
	public  void inicio() {
		
	    dao = new AssuntoMobileDao();
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
	public void deveTrazerEnderecoResidencial() {
		
		Long idAssunto = new Long(2001);
		Long idPessoa = new Long(201600057733L);
		
		try {
			dao.setEntityManager(em);
			dao.pagamentoRegistroEstaPago(idAssunto, idPessoa);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
			
	}


}
