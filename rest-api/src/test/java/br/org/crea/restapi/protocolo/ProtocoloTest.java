package br.org.crea.restapi.protocolo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.commons.Protocolo;

public class ProtocoloTest {
	
	public EntityManager em = null;
	public ProtocoloDao protocoloDao = null;
	
	@Before
	public void inicio() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		
		protocoloDao = new ProtocoloDao();
		protocoloDao.setEntityManager(em);

	}
	
	//@Test
	public void deveTrazerAnexosDoProtocolo() {
	
		List<Protocolo> listProtocoloAnexo = protocoloDao.getAnexosDoProtocoloPor(new Long(20107041369L));
		System.out.println(">>Qtd protocolos encontrados: " + listProtocoloAnexo.size() + "\n");
		
		for (Protocolo protocolo : listProtocoloAnexo) {
			
			System.out.println(">>> Id prt: " + protocolo.getIdProtocolo());
			System.out.println(">>> Numero prt: " + protocolo.getNumeroProtocolo() + "\n");
		}
		
	}
	
//	@Test
	public void deveTrazerApensosDoProtocolo() {
		
		List<Protocolo> listProtocoloAnexo = protocoloDao.getApensosDoProtocoloPor(new Long(1971200122L));
		System.out.println(">>Qtd protocolos encontrados: " + listProtocoloAnexo.size() + "\n");
		
		for (Protocolo protocolo : listProtocoloAnexo) {
			
			System.out.println(">>> Id prt: " + protocolo.getIdProtocolo());
			System.out.println(">>> Numero prt: " + protocolo.getNumeroProtocolo() + "\n");
		}
		
	}
	
	//@Test
	public void deveTrazerUmProtocolo() {
		
		Protocolo protocolo = protocoloDao.getProtocoloBy(new Long(2005729208L));
		System.out.println(">>> Numero protocolo: " + protocolo.getNumeroProtocolo());
	}

}
