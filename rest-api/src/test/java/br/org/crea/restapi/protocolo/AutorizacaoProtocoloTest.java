package br.org.crea.restapi.protocolo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.protocolo.RlAutorizacaoProtocoloDao;

public class AutorizacaoProtocoloTest {

	private RlAutorizacaoProtocoloDao dao = null;
	private EntityManager em = null;

	@Before
	public void inicio() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		
		dao = new RlAutorizacaoProtocoloDao();
		dao.setEntityManager(em);
	}
	
	@Test
	public void verificaSeFuncionarioPodeSubstituirProtocolo() {
		
		boolean teste = dao.podeSubstituirProtocolo(new Long(174));
		Assert.assertEquals(teste, true);
	}
	
}
