package br.org.crea.restapi.commons;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.atendimento.dao.GuicheDao;
import br.org.crea.commons.models.atendimento.dtos.AgendamentoDto;

public class GuicheDaoTest {
	static GuicheDao dao;
	private static EntityManager em = null;
	
	@Before
	public  void inicio() {
		
	    dao = new GuicheDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		Assert.assertTrue("entity manager iniciado", em != null);
	}
	
	@After
	public void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	
	@Test
	public void deveTrazerEnderecoResidencial() {
		
		AgendamentoDto dto = new AgendamentoDto();
		Long idFuncionario = new Long(2007138566);
		Long id = new Long(218277);
		dto.setId(id);
		dto.setIdFuncionario(idFuncionario);
		
		
		try {
			dao.setEntityManager(em);
//			dao.chamarCliente(dto);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
			
	}


}
