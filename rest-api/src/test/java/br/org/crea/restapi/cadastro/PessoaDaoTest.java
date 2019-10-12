package br.org.crea.restapi.cadastro;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;

public class PessoaDaoTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private PessoaDao dao;

	@Before
	public void inicio() {
		emf = Persistence.createEntityManagerFactory("dsCreaTest");
		em = emf.createEntityManager();
		dao = new PessoaDao();
		dao.setEntityManager(em);
	}
	
	@Test
	public void possuiSituacaoRegistroAtivaTest() {
		boolean possui = dao.possuiSituacaoRegistroAtiva(1999106498L);
		assertEquals(true, possui);
	}
	
	@Test
	public void naoPossuiSituacaoRegistroAtivaTest() {
		boolean possui = dao.possuiSituacaoRegistroAtiva(1968100231L);
		assertEquals(false, possui);
	}
	
	@Test
	public void verificaRegistroEnquadradoArtigo64Test() {
		boolean possui = dao.verificaRegistroEnquadradoArtigo64(1970101593L, "PROFISSIONAL");
		assertEquals(true, possui);
	}
	
	
}
