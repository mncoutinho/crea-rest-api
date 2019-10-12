package br.org.crea.restapi.art.contrato;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.ArtTipoTaxa;
import br.org.crea.commons.models.art.enuns.TipoTaxaArtEnum;

public class TaxaMinimaObraServicoTest {

	ContratoArtDao dao;
	ArtDao artDao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		dao = new ContratoArtDao();
		artDao = new ArtDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao.setEntityManager(em);
		artDao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void deveAtualizarValorDaArtEDoContrato() {
		dao.createTransaction();
		ContratoArt contratoArt = dao.getContratoPor("2020180000476-1");
		assertTrue(this.defineTaxaMinimaObraServico(contratoArt));
		dao.commitTransaction();
	}
	
	private boolean defineTaxaMinimaObraServico(ContratoArt contrato) {
		
		BigDecimal valorArt = artDao.getTaxaPorDataBaseETipoTaxa(new Date(), TipoTaxaArtEnum.TAXA_ESPECIAL_1);
		contrato.setValorReceber(valorArt);
		contrato.setValorCalculado(valorArt);
		ArtTipoTaxa tipoTaxaContrato = new ArtTipoTaxa();
		tipoTaxaContrato.setId(TipoTaxaArtEnum.TAXA_ESPECIAL_1.getId());
		contrato.setTipoTaxa(tipoTaxaContrato);
		contrato.getArt().setValorReceber(valorArt);
		contrato.getArt().setTipoTaxa(tipoTaxaContrato);
		dao.atualizaValorReceberCalculadoETipoTaxa(contrato);
		artDao.atualizaValorReceberETipoTaxa(contrato.getArt());
		return true;
	}
}
