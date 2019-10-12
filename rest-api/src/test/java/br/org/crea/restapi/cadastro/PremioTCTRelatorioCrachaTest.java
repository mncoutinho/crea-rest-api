package br.org.crea.restapi.cadastro;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.cadastro.PremioTCTDao;
import br.org.crea.commons.models.cadastro.ParticipantePremioTCT;
import br.org.crea.commons.models.cadastro.dtos.premio.PremioTCTDto;

public class PremioTCTRelatorioCrachaTest {
	static PremioTCTDao dao;
	private static EntityManager em = null;
	
	@Before
	public  void inicio() {
		
	    dao = new PremioTCTDao();
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
	public void PremioTest() {
		
		try {
			dao.setEntityManager(em);
			
			PremioTCTDto premio = new PremioTCTDto();
			
			premio.setAno(new Long(2018));
			
//			List<ParticipantePremioTCT> listaRelatorio = dao.relatorioCracha(premio);
//			
//			listaRelatorio.forEach(participante -> {
//				System.out.println(participante.getPessoa().getNome() + " | " + participante.getPapel() + " | " + participante.getPremio().getTitulo()
//						+ " | " + participante.getPremio().getNomeInstituicaoEnsino());
//			});
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
