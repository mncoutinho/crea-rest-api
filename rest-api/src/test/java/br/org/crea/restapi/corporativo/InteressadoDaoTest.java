package br.org.crea.restapi.corporativo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;

public class InteressadoDaoTest {
	
	
	//@Test
	public void deveTrazerProfissional(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
		EntityManager em = emf.createEntityManager();
		
		try {
			
			InteressadoDao dao = new InteressadoDao();
			dao.setEntityManager(em);
			
			IInteressado interessado = null;
			interessado =  dao.buscaInteressadoBy(new Long(1949100125L));
			
			
			System.out.println(interessado);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void deveTrazerInstituicaoEnsino(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
		EntityManager em = emf.createEntityManager();
		
		try {
			
			InteressadoDao dao = new InteressadoDao();
			dao.setEntityManager(em);
			
			IInteressado interessado = null;
			interessado =  dao.buscaInteressadoBy(new Long(10100504800L));
			
			
			System.out.println(interessado.getNomeRazaoSocial());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
