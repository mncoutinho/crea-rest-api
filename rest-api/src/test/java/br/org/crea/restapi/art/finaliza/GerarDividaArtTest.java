package br.org.crea.restapi.art.finaliza;

import static org.junit.Assert.assertTrue;

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
import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.financeiro.NaturezaDivida;
import br.org.crea.commons.models.financeiro.StatusDivida;
import br.org.crea.commons.util.DateUtils;

public class GerarDividaArtTest {

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
		assertTrue(this.gerarDividaArt());
		finDividaDao.commitTransaction();
	}
	
	private boolean gerarDividaArt() { // linha 1265, 8164 processarRegrasFinanceirasDaArt

		if (contrato.getArt().getIsAcaoOrdinaria()) {
			return false;
		}
		
		FinDivida divida = new FinDivida();
	
		divida.setData(new Date());
		divida.setDataVencimento(defineDataDeVencimentoDividaArt());
		divida.setValorOriginal(contrato.getArt().getValorReceber());
		divida.setValorAtual(contrato.getArt().getValorReceber());
		divida.setDataValorAtualizado(DateUtils.getUltimoDiaDoAnoCorrente());
		divida.setIdentificadorDivida(contrato.getArt().getNumero());
		divida.setNatureza(populaNaturezaDividaArt());
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
		
		return true;
		
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
	
	/** Define data de vencimento da art após seu cadastramento.
	 *  Se a dataFim estiver entre amanhã e daqui dez dias, a data de vencimento será igual a dataFim,
	 *  caso contrário o prazo de vencimento será de dez dias.
	 *  No caso da múltipla mensal, o vencimento é até o décimo dia útil do mês seguinte.
	 * 
	 * @return Date
	 */
	private Date defineDataDeVencimentoDividaArt() {
		
		Date hoje = new Date();
		Date hojeMaisDezDias = DateUtils.adicionaOrSubtraiDiasA(hoje, 10);
		Date dataDeVencimento = hojeMaisDezDias;
		
		if (contrato.artNaoEhMultiplaMensal()) {
			Date amanha = DateUtils.adicionaOrSubtraiDiasA(hoje, 1);
			
			if (contrato.temDataFim()) {
				if (DateUtils.primeiraDataeMaiorOuIgualQueSegunda(contrato.getDataFim(), amanha) &&
				DateUtils.primeiraDataeMenorIgualQueSegunda(contrato.getDataFim(), hojeMaisDezDias)) {
					dataDeVencimento = contrato.getDataFim();
				}
			}
		}
		
		if (contrato.artEhMultiplaMensal()) {
			dataDeVencimento = DateUtils.getEnesimoDiaUtilDoMesAno(10, DateUtils.format(contrato.getDataInicio(), DateUtils.MM) , DateUtils.format(contrato.getDataInicio(), DateUtils.YYYY));
		}
		
		return dataDeVencimento;
	}

}
