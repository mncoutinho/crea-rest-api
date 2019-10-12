package br.org.crea.restapi.art;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ArtGeradorSequenciaDao;
import br.org.crea.commons.dao.art.ContratoArtDao;

public class AplicarModeloTest {

	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	private ArtDao dao;
	
	private ArtGeradorSequenciaDao geradorSequenciaDao;
	
	private ContratoArtDao contratoDao;
	
	@Before
	public void setup(){
		 //Cria conexão com o banco de teste
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();
		 
		 //instancia o dao
		 dao = new ArtDao();
		 geradorSequenciaDao = new ArtGeradorSequenciaDao();
		 contratoDao = new ContratoArtDao();
		 dao.setEntityManager(em);
		 geradorSequenciaDao.setEntityManager(em);
		 contratoDao.setEntityManager(em);
	}
	
	@Test
	public void aplicarModeloArtTest(){ 
		dao.createTransaction();
		String numeroArtModelo = "2020180000932";
		String novoNumero = geradorSequenciaDao.getSequenciaArt();
		dao.aplicarModeloArt(numeroArtModelo, novoNumero);
		System.out.println(novoNumero);
		dao.commitTransaction();
		
//		contratoDao.createTransaction();
//		contratoDao.aplicarModeloContrato(numeroArtModelo, novoNumero);
//		contratoDao.commitTransaction();
		
		contratoDao.createTransaction();
		contratoDao.aplicarModeloAtividades(numeroArtModelo, novoNumero);
		contratoDao.commitTransaction();
		
		contratoDao.createTransaction();
		contratoDao.aplicarModeloEspecificacoes(numeroArtModelo, novoNumero);
		contratoDao.commitTransaction();
		
		contratoDao.createTransaction();
		contratoDao.aplicarModeloComplementos(numeroArtModelo, novoNumero);
		contratoDao.commitTransaction();
		
		contratoDao.createTransaction();
		contratoDao.aplicarModeloQuantificacao(numeroArtModelo, novoNumero);
		contratoDao.commitTransaction();
	}

	@After
	public void finishTest(){
		em.close();
		emf.close();
	}

}
