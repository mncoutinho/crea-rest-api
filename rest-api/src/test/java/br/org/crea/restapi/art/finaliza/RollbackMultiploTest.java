package br.org.crea.restapi.art.finaliza;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.financeiro.NaturezaDivida;
import br.org.crea.commons.models.financeiro.StatusDivida;

public class RollbackMultiploTest {

	private ContratoArtDao contratoDao;

	private ArtDao artDao;
	
	FinDividaDao finDividaDao;

	private Art art;
	
	private String numeroArt;

	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		artDao = new ArtDao();
		contratoDao = new ContratoArtDao();
		finDividaDao = new FinDividaDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		contratoDao.setEntityManager(em);
		artDao.setEntityManager(em);
		finDividaDao.setEntityManager(em);
		Assert.assertTrue("entity manager iniciado", em != null);
	}

	@After
	public void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen());
	}
	
	@Test
	public void deveRetornarTrueCasoRollbackMultiploOcorra() {
		Assert.assertTrue(this.getTaxa());
	}

	public boolean getTaxa() {
		FinDivida divida = new FinDivida();
		finDividaDao.createTransaction();
//		artDao.createTransaction();
		
		try {
			divida.setData(new Date());
			divida.setDataVencimento(new Date()); 
			divida.setValorOriginal(new BigDecimal("0"));
			divida.setValorAtual(new BigDecimal("0"));
			divida.setDataValorAtualizado(new Date());
			divida.setIdentificadorDivida("teste rollback ok");
			divida.setNatureza(populaNaturezaDividaArt());
			divida.setParcela(0);
			divida.setStatus(populaStatusDividaAVencer());
			divida.setTipoPessoa(populaTipoPessoaDivida());
			
			Pessoa pessoa = new Pessoa();
			pessoa.setId(1999106498L);
			divida.setPessoa(pessoa);
			divida.setObservacao("Dívida gerada automaticamente pelo sistema de ART.");
			
			finDividaDao.create(divida);
			divida.setIdentificadorDivida("teste rollback falha");
			divida.setPessoa(populaPessoaDividaArt());
			
//			artDao.atualizaTaxaMinima("2020180000621", true);
			
			finDividaDao.create(divida);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			finDividaDao.roolbackTransaction();
//			artDao.roolbackTransaction();
			return false;
		}
		finDividaDao.commitTransaction();
//		artDao.commitTransaction();
		divida = finDividaDao.getBy(divida.getId());
		
		return divida != null;
	}
	
	private NaturezaDivida populaNaturezaDividaTaxaDeIncorporacao() {
		NaturezaDivida naturezaDivida = new NaturezaDivida();
		naturezaDivida.setId(new Long(800));
		return naturezaDivida;
	}
	
	private TipoPessoa populaTipoPessoaDivida() {
		return TipoPessoa.PROFISSIONAL;
	}

	private Pessoa populaPessoaDividaArt() {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(19997L);
		return pessoa;
	}

	private NaturezaDivida populaNaturezaDividaArt() {
		NaturezaDivida naturezaDivida = new NaturezaDivida();
		naturezaDivida.setId(new Long(1));
		return naturezaDivida;
	}
	
	private StatusDivida populaStatusDividaAVencer() {
		StatusDivida statusDivida = new StatusDivida();
		statusDivida.setId(new Long(1));
		return statusDivida;
	}
}
