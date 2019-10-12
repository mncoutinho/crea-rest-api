package br.org.crea.restapi.art.contrato;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.models.art.ContratoArt;

public class VerificaSeEhMetroQuadradoTest {

	ContratoArtDao dao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		dao = new ContratoArtDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void EhMetroQuadradoTest() {
		boolean resultado = verificaSeEhMetroQuadrado("2020180000476-1"); 
//		assertTrue(resultado);
		System.out.println("Resultado: " + resultado);
	}
	
	public boolean verificaSeEhMetroQuadrado(String numeroContrato) {
		ContratoArt contratoArt = dao.getContratoPor(numeroContrato);
		contratoArt.setListCodigoAtividades(dao.getListaDeCodigosDasAtividadesDoContratoPor(contratoArt.getId()));
		contratoArt.setListCodigoEspecificacoes(dao.getListaDeCodigosDasEspecificacoesDoContratoPor(contratoArt.getId()));
		contratoArt.setListCodigoComplementos(dao.getListaDeCodigosDosComplementosDoContratoPor(contratoArt.getId()));
		contratoArt.setIdStringModalidade("1");
		return dao.verificaSeEhMetroQuadrado(contratoArt);
	}
}
