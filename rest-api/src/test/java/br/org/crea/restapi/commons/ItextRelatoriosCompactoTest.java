package br.org.crea.restapi.commons;

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
import br.org.crea.commons.util.ItextUtil;

public class ItextRelatoriosCompactoTest {

	static RelatorioReuniaoSiacolDao dao;
	private static EntityManager em = null;
	private List<RelatorioReuniaoSiacolDto> lista;

	@Before
	public void setup() {
		dao = new RelatorioReuniaoSiacolDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		Assert.assertTrue("entity manager iniciado", em != null);
		lista = populaAgendamentos();
	}
	
	@After
	public void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	private List<RelatorioReuniaoSiacolDto> populaAgendamentos() {
		dao.setEntityManager(em);
		
		List<RelatorioReuniaoSiacolDto> lista = dao.relatorioDeCoordenadoresEAdjuntosPorDepartamento(new Long(10055));
		
		return lista;
	}


	@Test
	public void relatorioItext() {

		// parametros
		String nomeArquivo = "relatorio";

//		ItextUtil.iniciarDocumento(nomeArquivo);
		ItextUtil.adicionaCabecalhoPadrao();
		ItextUtil.adicionaRodapePadrao();
//		ItextUtil.adicionaTabelaCoordenadoresAoConteudo(lista);
	}
}
