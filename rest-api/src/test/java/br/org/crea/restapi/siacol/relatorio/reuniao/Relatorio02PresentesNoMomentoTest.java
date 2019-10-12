package br.org.crea.restapi.siacol.relatorio.reuniao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.RelatorioReuniaoSiacolDao;
import br.org.crea.commons.models.siacol.dtos.RelatorioReuniaoSiacolDto;

public class Relatorio02PresentesNoMomentoTest {

	static RelatorioReuniaoSiacolDao dao;
	private static EntityManager em = null;
	
	@Before
	public  void inicio() {
	    dao = new RelatorioReuniaoSiacolDao();
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
	public void relatoriosTest() {
		
		try {
			dao.setEntityManager(em);
			Long idReuniao = 20050L;
			List<RelatorioReuniaoSiacolDto> listaRelatorio = dao.relatorioPresentesNoMomento(idReuniao);
			
			listaRelatorio.forEach(item -> {
				System.out.println(item.getNome());
			});
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
