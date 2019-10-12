package br.org.crea.restapi.financeiro;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.util.DateUtils;

public class DividaFinanciamentoTest {
	
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
	public void deveTrazerUltimaAnuidadePaga() {
	
		FinDividaDao finDividaDao = new FinDividaDao();
		finDividaDao.setEntityManager(em);
		
		FinDivida anuidade = finDividaDao.getUltimaAnuidadePagaPor(new Long(2011106067));
		System.out.println(">>> Anuidade período: " + anuidade.getIdentificadorDivida() + " >>> Codigo Divida: " + anuidade.getId());
	}
	
	@Test
	public void deveTrazerParcelaAnuidadePaga() {
	
		FinDividaDao finDividaDao = new FinDividaDao();
		finDividaDao.setEntityManager(em);
		
		FinDivida anuidade = finDividaDao.getUltimaParcelaAnuidadeVencidaPagaPor(new Long(2011106067));
		System.out.println(">>> Anuidade período: " + anuidade.getIdentificadorDivida() + " >>> Codigo Divida: " + anuidade.getId());
		System.out.println(">>> Pago em : " + DateUtils.format(anuidade.getDataQuitacao(), DateUtils.DD_MM_YYYY));
	}

}
