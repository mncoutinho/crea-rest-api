package br.org.crea.restapi.cadastro;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.EmailDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.profissional.CarteiraDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;

public class CarteiraSolicitacaoTest {
	
private EntityManagerFactory emf;
	
	private EntityManager em;
	private CarteiraDao dao;
	private ProfissionalDao profissionalDao;
	private EmailDao emailDao;
	private EnderecoDao enderecoDao;
	
	@Before
	public void setup(){
		 //Cria conexão com o banco de teste
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();
		 
		 //instancia o dao
		 dao = new CarteiraDao();
		 profissionalDao = new ProfissionalDao();
		 emailDao = new EmailDao();
		 enderecoDao = new EnderecoDao();
		 dao.setEntityManager(em);
		 profissionalDao.setEntityManager(em);
		 emailDao.setEntityManager(em);
		 enderecoDao.setEntityManager(em);
	}
	
	@After
	public void finishTest(){
		em.close();
		emf.close();
	}
	
	@Test
	public void deveValidarSolicitacaoDeSegundaViaTest(){ 
		Long idPessoa = 1970101593L;
		
		assertTrue(dao.ultimaCarteiraAtivaEhDefinitiva(idPessoa));
		assertFalse(dao.existePedidoNaFilaDeImpressao(idPessoa));
		assertTrue(profissionalDao.profissionalPossuiRnp(idPessoa));
		assertTrue(emailDao.pessoaPossuiEmailCadastrado(idPessoa));
		assertTrue(enderecoDao.possuiEnderecoValidoEPostalPor(idPessoa));
	}
	
	@Test
	public void deveInvalidarSolicitacaoDeSegundaViaPorNaoPossuiFilaImpressaoTest(){ 
		Long idPessoa = 1950100230L;
		
		assertFalse(dao.existePedidoNaFilaDeImpressao(idPessoa));
	}
	
	@Test
	public void deveInvalidarSolicitacaoDeSegundaViaPorNaoPossuiRnpTest(){ 
		Long idPessoa = 1950100230L;
		
		assertFalse(profissionalDao.profissionalPossuiRnp(idPessoa));
	}
	
	@Test
	public void deveInvalidarSolicitacaoDeSegundaViaPorNaoPossuiEmailTest(){ 
		Long idPessoa = 1950100230L;
		
		assertTrue(emailDao.pessoaPossuiEmailCadastrado(idPessoa));
	}
	
	@Test
	public void deveInvalidarSolicitacaoDeSegundaViaPorNaoPossuiEnderecoTest(){ 
		Long idPessoa = 1950100230L;
		assertTrue(enderecoDao.possuiEnderecoValidoEPostalPor(idPessoa));
	}
	

	

}
