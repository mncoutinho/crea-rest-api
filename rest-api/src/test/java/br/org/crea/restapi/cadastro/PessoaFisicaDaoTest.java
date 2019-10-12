package br.org.crea.restapi.cadastro;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.pessoa.PessoaFisicaDao;

public class PessoaFisicaDaoTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private PessoaFisicaDao dao;

	@Before
	public void inicio() {
		emf = Persistence.createEntityManagerFactory("dsCreaTest");
		em = emf.createEntityManager();
		dao = new PessoaFisicaDao();
		dao.setEntityManager(em);
	}
	
	@Test
	public void possuiFotoTest() {
		boolean possui = dao.possuiFoto(1999106498L);
		assertEquals(true, possui);
	}
	
	@Test
	public void naoPossuiFotoTest() {
		boolean possui = dao.possuiFoto(1968100231L);
		assertEquals(false, possui);
	}
	
	@Test
	public void possuiAssinaturaTest() {
		boolean possui = dao.possuiAssinatura(1999106498L);
		assertEquals(true, possui);
	}
	
	@Test
	public void naoPossuiAssinaturaTest() {
		boolean possui = dao.possuiAssinatura(1968100231L);
		assertEquals(false, possui);
	}
	
}
