package br.org.crea.restapi.cadastro;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.empresa.ObjetoSocialDao;
import br.org.crea.commons.models.cadastro.ObjetoSocial;

public class EmpresaTest {
	
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
	
	//@Test
	public void deveTrazerUltimoObjetoSocialCadastradoEmpresaNovaInscrita() {
	
		ObjetoSocialDao objetoSocialDao = new ObjetoSocialDao();
		objetoSocialDao .setEntityManager(em);
		
		ObjetoSocial objetoSocial = objetoSocialDao.getUltimoObjetoSocialEmpresaNovaInscritaPor(new Long(2012063573));
		System.out.println(">>> Objeto Social: " + objetoSocial.getDescricao());
	}
	
	@Test
	public void deveTrazerUltimoObjetoSocialCadastradoEmpresaCadastrada() {
	
		ObjetoSocialDao objetoSocialDao = new ObjetoSocialDao();
		objetoSocialDao .setEntityManager(em);
		
		ObjetoSocial objetoSocial = objetoSocialDao.getUltimoObjetoSocialEmpresaCadastradaPor(new Long(1986200715));
		System.out.println(">>> Objeto Social: " + objetoSocial.getDescricao());
	}

}
