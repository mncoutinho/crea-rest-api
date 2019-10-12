package br.org.crea.restapi.art.contrato;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.models.art.enuns.TipoTaxaArtEnum;

public class CalculaValorTotalTaxaMultiplaTest {
	ArtDao artDao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		artDao = new ArtDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		artDao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void deveRetornarValorJaSomadoQuandoTaxaMultipla() {
		
		assertEquals(new BigDecimal("1226.82"), artDao.getValorTotalDosContratosPelaTaxaMultipla("IN00739925", TipoTaxaArtEnum.MULTIPLA, new Date()));
	}
}
