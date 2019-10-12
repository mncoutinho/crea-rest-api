package br.org.crea.restapi.cadastro;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.empresa.RequerimentoPJDao;

public class RequerimentoPJTest {
	
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
	public void deveTrazerUltimoRequerimentoDeEmpresa() {
	
		RequerimentoPJDao dao = new RequerimentoPJDao();
		dao.setEntityManager(em);
		
		//RequerimentoPessoaJuridica requerimento = dao.buscaRequerimentoPor(new Long(2017200045));
		//System.out.println(">> ID Requerimento: " + requerimento.getId());
	}

}
