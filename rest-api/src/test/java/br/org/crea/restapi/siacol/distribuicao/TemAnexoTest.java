package br.org.crea.restapi.siacol.distribuicao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;

public class TemAnexoTest {

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
	
	@Test
	public void temAnexoTest() {
		Long protocolo = 201870063987L;
		boolean temAnexo = dao.temAnexoNoSiacol(protocolo);
		boolean estaAnexado = dao.estaAnexadoAProtocoloNoSiacol(protocolo);
		
		System.out.print("O Protocolo " + protocolo);
		System.out.print(temAnexo ? "" : " não");
		System.out.print(" tem Anexo e ");
		System.out.print(estaAnexado ? "" : "não ");
		System.out.print("está anexado.");
	}
}
