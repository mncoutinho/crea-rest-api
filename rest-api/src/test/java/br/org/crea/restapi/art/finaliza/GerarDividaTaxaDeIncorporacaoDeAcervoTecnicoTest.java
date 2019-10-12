package br.org.crea.restapi.art.finaliza;

import static org.junit.Assert.assertTrue;

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
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.enuns.TipoTaxaArtEnum;
import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.financeiro.NaturezaDivida;
import br.org.crea.commons.models.financeiro.StatusDivida;
import br.org.crea.commons.util.DateUtils;

public class GerarDividaTaxaDeIncorporacaoDeAcervoTecnicoTest {

	ContratoArtDao dao;
	ArtDao artDao;
	private static EntityManager em = null;
	
	ContratoArt contrato;
	
	FinDividaDao finDividaDao;
	
	@Before
	public void inicio() {
		finDividaDao = new FinDividaDao();
		dao = new ContratoArtDao();
		artDao = new ArtDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao.setEntityManager(em);
		artDao.setEntityManager(em);
		finDividaDao.setEntityManager(em);
		contrato = new ContratoArt();
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void deveGerarDividaArt() {
		contrato = dao.getContratoPor("2020180000525-1");
		finDividaDao.createTransaction();
		assertTrue(this.gerarDividaDoResgate());
		finDividaDao.commitTransaction();
	}
	
	private boolean gerarDividaDoResgate() {

		if (contrato.getArt().getIsAcaoOrdinaria()) {
			return false;
		}
		
		// se nao eh resgate ou eh de termo aditivo ...
		
		
		FinDivida divida = new FinDivida();
		
		Date hoje = new Date();
		
		BigDecimal valorTaxaDeIncorporacao = artDao.getTaxaPorDataBaseETipoTaxa(new Date(), TipoTaxaArtEnum.TAXA_RESGATE_ACERVO);
		
		divida.setData(hoje);
		divida.setDataVencimento(hoje); // vencimento é no próprio dia?????? // linha 2295 legado
		divida.setValorOriginal(valorTaxaDeIncorporacao);
		divida.setValorAtual(valorTaxaDeIncorporacao);
		divida.setDataValorAtualizado(DateUtils.getUltimoDiaDoAnoCorrente());
		divida.setIdentificadorDivida(contrato.getArt().getNumero());
		divida.setNatureza(populaNaturezaDividaTaxaDeIncorporacao());
		divida.setParcela(0);
		divida.setStatus(populaStatusDividaAVencer());
		divida.setTipoPessoa(populaTipoPessoaDivida());
		divida.setPessoa(populaPessoaDividaArt());
		divida.setObservacao("Dívida gerada automaticamente pelo sistema de ART.");
		
		divida.setDiati(false);
		divida.setScpc(false);
		divida.setScpcBaixa(false);
		divida.setScpcRepasse(false);
		divida.setRepasseOk(false);
		
		finDividaDao.create(divida);
		
		
//		Exigencia nao analisada
		
//		ExigenciaART exigenciaART = exigenciaARTFacade.buscarByARTECodigo(art.getNumero(), new Long(50));
//		Exigencia exigencia = exigenciaFacade.buscarPorCodigo(new Long(50));
//		if ( exigenciaART == null && finDivida.getDataPgto() == null ) {
//			exigenciaART = new ExigenciaART();
//			exigenciaART.setData(Calendar.getInstance());
//			exigenciaART.setArt(art);
//			exigenciaART.setExigencia(exigencia);
//			exigenciaART.setTipoAcaoART(tipoAcaoARTFacade.buscarPorCodigo(new Long(0)));
//			exigenciaART.setMotivo(exigencia.getDescricao());
//
//			exigenciaARTFacade.cadastrar(exigenciaART);
//		}
		
		return true;
	}

	private NaturezaDivida populaNaturezaDividaTaxaDeIncorporacao() {
		NaturezaDivida naturezaDivida = new NaturezaDivida();
		naturezaDivida.setId(new Long(800));
		return naturezaDivida;
	}

	private TipoPessoa populaTipoPessoaDivida() {
		return contrato.getArt().temEmpresa() ? TipoPessoa.EMPRESA : TipoPessoa.PROFISSIONAL;
	}

	private Pessoa populaPessoaDividaArt() {
		Pessoa pessoa = new Pessoa();
		if (contrato.getArt().temEmpresa()) {
			pessoa.setId(contrato.getArt().getEmpresa().getPessoaJuridica().getId());
		} else {
			pessoa.setId(contrato.getArt().getProfissional().getPessoaFisica().getId());
		}
		return pessoa;
	}
	
	private StatusDivida populaStatusDividaAVencer() {
		StatusDivida statusDivida = new StatusDivida();
		statusDivida.setId(new Long(1));
		return statusDivida;
	}
	
}
