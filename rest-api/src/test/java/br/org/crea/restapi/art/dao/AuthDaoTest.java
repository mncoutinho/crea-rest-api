package br.org.crea.restapi.art.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.AuthDao;

public class AuthDaoTest {
	
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	private AuthDao dao;
	
	@Before
	public void setup(){
		 //Cria conexão com o banco de teste
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();
		 
		 //instancia o dao
		 dao = new AuthDao(); 
		 dao.setEntityManager(em);
	}
	
	@Test
	public void profissionalDevePossuirEventoTransferidoCAUComDataFinalVazia() {
		assertTrue(dao.profissionalPossuiEventoTransferidoCAUComDataFinalVazia("1982200384"));
	}
	
	@Test
	public void profissionalNaoDevePossuirEventoTransferidoCAUComDataFinalVazia() {
		assertFalse(dao.profissionalPossuiEventoTransferidoCAUComDataFinalVazia("1999106498"));
	}
	
	@Test
	public void profissionalDevePossuirEventoTransferidoParaCFTComDataFinalVazia() {
		assertTrue(dao.profissionalPossuiEventoTransferidoParaCFTComDataFinalVazia("1985101382"));
	}
	
	@Test
	public void profissionalDevePossuirEventoFalecidoComDataFinalVazia() {
		assertTrue(dao.profissionalPossuiEventoFalecidoComDataFinalVazia("1948100038"));
	}
	
	@Test
	public void profissionalNaoDevePossuirEventoFalecidoComDataFinalVazia() {
		assertFalse(dao.profissionalPossuiEventoFalecidoComDataFinalVazia("1999106498"));
	}
	
	@Test
	public void profissionalDevePossuirSituacaoRegistroExcluidoOuSuspenso() {
		assertTrue(dao.profissionalPossuiEventoExcluidoOuSuspenso("1948100036"));
	}
	
	@Test
	public void profissionalNaoDevePossuirSituacaoRegistroExcluidoOuSuspenso() {
		assertFalse(dao.profissionalPossuiEventoExcluidoOuSuspenso("1999106498"));
	}
	
	@Test
	public void profissionalDevePossuiSituacaoRegistroNovoInscritoOuDataRegistroVazia() {
		assertTrue(dao.profissionalPossuiSituacaoRegistroNovoInscritoOuDataRegistroVazia("2018100029"));
	}
	
	@Test
	public void profissionalNaoDevePossuiSituacaoRegistroNovoInscritoOuDataRegistroVazia() {
		assertFalse(dao.profissionalPossuiSituacaoRegistroNovoInscritoOuDataRegistroVazia("1999106498"));
	}
	
	@Test
	public void empresaDevePossuirEventoTransferidoCAU() {
		assertTrue(dao.empresaPossuiEventoTransferidoCAU("1982200384"));
	}
	
	@Test
	public void empresaNaoDevePossuirEventoTransferidoCAU() {
		assertFalse(dao.empresaPossuiEventoTransferidoCAU("1968200067"));
	}
	
	@Test
	public void empresaDevePossuirEventoExcluidoOuSuspensoOuInterropidoOuSemValidade() {
		assertTrue(dao.empresaPossuiEventoExcluidoOuSuspensoOuInterropidoOuSemValidade("1982200384"));
	}
	
	@Test
	public void empresaNaoDevePossuirEventoExcluidoOuSuspensoOuInterropidoOuSemValidade() {
		assertFalse(dao.empresaPossuiEventoExcluidoOuSuspensoOuInterropidoOuSemValidade("1968200067"));
	}
	
	@After
	public void finishTest(){
		em.close();
		emf.close();
	}

}
