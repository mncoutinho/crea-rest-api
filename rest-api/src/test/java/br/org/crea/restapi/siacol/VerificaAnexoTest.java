package br.org.crea.restapi.siacol;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;

public class VerificaAnexoTest {

	static ProtocoloSiacolDao dao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new ProtocoloSiacolDao();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
//	@Test
	public void temAnexoTest() {
		Long numeroProtocolo = 201170053331L;
		System.out.println("Tem Anexo: " + dao.temAnexoNoSiacol(numeroProtocolo));
	}
	
//	@Test
	public void estaAnexadoTest() {
		Long numeroProtocolo = 201170053331L;
		System.out.println("Está Anexado: " + dao.estaAnexadoAProtocoloNoSiacol(numeroProtocolo));
	}
	
	@Test
	public void getProtocoloPaiNoSiacolTest() {
		Long numeroProtocolo = 201170053331L;
		System.out.println("Protocolo pai no Siacol: " + dao.getNumeroProtocoloPaiNoSiacol(numeroProtocolo));
	}
	
//	@Test
	public void getListAnexosSiacolTest() {
		Long numeroProtocoloPai = 20107041369L;
		List<ProtocoloSiacol> protocolos = dao.getListProtocolosAnexosNoSiacol(numeroProtocoloPai);
		protocolos.forEach(protocolo -> {
			System.out.println(protocolo.getNumeroProtocolo());
		});
	}
	
}
