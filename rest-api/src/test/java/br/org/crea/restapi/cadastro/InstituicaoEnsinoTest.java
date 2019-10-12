package br.org.crea.restapi.cadastro;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.InstituicaoEnsinoCadastroDao;
import br.org.crea.commons.models.cadastro.CampusCadastro;
import br.org.crea.commons.models.cadastro.CursoCadastro;
import br.org.crea.commons.models.commons.dtos.InstituicaoEnsinoCadastroDto;

public class InstituicaoEnsinoTest {
	
	private static EntityManager em = null;

	@BeforeClass
	public static void inicio() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		Assert.assertTrue("entity manager iniciado", em != null);
	}
	
	@AfterClass
	public static void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	@Test
	public void deveTrazerListaDeInstituicaoEnsino() {
		
		InstituicaoEnsinoCadastroDao dao = new InstituicaoEnsinoCadastroDao();
		dao .setEntityManager(em);
		
		List<InstituicaoEnsinoCadastroDto> lista = dao.getInstituicaoEnsinoDTOListByUF();
		
		Assert.assertNotNull(lista);
	}
	
	
	@Test
	public void deveTrazerListaDeCampusPeloIdDaInstituicaoEnsino() {
		
		InstituicaoEnsinoCadastroDao dao = new InstituicaoEnsinoCadastroDao();
		dao .setEntityManager(em);
		
		List<CampusCadastro> lista = dao.getCampusListByInstituicaoEnsinoCadastroId(10100157000l);
		
		Assert.assertNotNull(lista);
	}
	
	@Test
	public void deveTrazerListaDeCurssPeloIdDoCampus() {
		
		InstituicaoEnsinoCadastroDao dao = new InstituicaoEnsinoCadastroDao();
		dao .setEntityManager(em);
		
		List<CursoCadastro> lista = dao.getCursoListByCampusCadastroId(56000250536200l);
		
		Assert.assertNotNull(lista);
	}
	
	

}
