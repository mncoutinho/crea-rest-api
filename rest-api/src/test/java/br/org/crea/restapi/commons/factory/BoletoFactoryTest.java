package br.org.crea.restapi.commons.factory;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.financeiro.BoletoDao;
import br.org.crea.commons.dao.financeiro.FinGeradorNossoNumeroDao;
import br.org.crea.commons.dao.financeiro.FinPrecoTaxaDao;
import br.org.crea.commons.models.financeiro.Boleto;
import br.org.crea.commons.models.financeiro.enuns.FinNaturezaEnum;
import br.org.crea.commons.models.financeiro.enuns.StatusBoletoEnum;
import br.org.crea.commons.models.financeiro.enuns.StatusEmissaoBoletoEnum;
import br.org.crea.commons.util.DateUtils;

public class BoletoFactoryTest {

	private EntityManagerFactory emf;
	
	private EntityManager em;
	private BoletoDao boletoDao;
	private FinGeradorNossoNumeroDao finGeradorNossoNumeroDao;
	private FinPrecoTaxaDao finPrecoTaxaDao;
	
	@Before
	public void setup() {
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();

		 boletoDao = new BoletoDao();
		 finGeradorNossoNumeroDao = new FinGeradorNossoNumeroDao();
		 finPrecoTaxaDao = new FinPrecoTaxaDao();
		 boletoDao.setEntityManager(em);
		 finGeradorNossoNumeroDao.setEntityManager(em);
		 finPrecoTaxaDao.setEntityManager(em);
	}
	
	@After
	public void finishTest() {
		em.close();
		emf.close();
	}
	
	@Test
	public void cadastraBoletoTaxaDeSegundaViaFactoryTest () {
		
		Long idBoleto = this.cadastraBoletoTaxaDeSegundaViaDeCarteira(1970101593L);
		System.out.println("IdBoleto: "+ idBoleto);
	}
	
	
	public Long cadastraBoletoTaxaDeSegundaViaDeCarteira(Long idPessoa) {
		
		Boleto boleto = new Boleto();
		int exercicio = DateUtils.getAnoCorrente();
				
		try {
			boleto.setValorAtual(finPrecoTaxaDao.getValorTaxaPorNaturezaEExercicio(FinNaturezaEnum.EXPEDICAO_CARTEIRA.getId(), exercicio));
			boleto.setValorOriginal(boleto.getValorAtual());
			boleto.setAtivo(true);
			boleto.setDataVencimento(new Date());
			boleto.setIdConvenio(boletoDao.getConvenioByNatureza(FinNaturezaEnum.EXPEDICAO_CARTEIRA.getId()));
			boleto.setIdPessoa(idPessoa);
		
			boleto.setDescontoAbatimento(new BigDecimal(0));
			boleto.setInstrucao("Taxa " + FinNaturezaEnum.EXPEDICAO_CARTEIRA.getDescricao() + " - Exercício " + exercicio);
			boleto.setNossoNumero(finGeradorNossoNumeroDao.gerarNossoNumeroComConvenio(boleto.getIdConvenio()));
			boleto.setIdStatusBoleto(StatusBoletoEnum.VIGENTE.getId());
			boleto.setCota(new Long(0));
			boleto.setStatusEmissao(StatusEmissaoBoletoEnum.EMITIDO);

			boletoDao.createTransaction();
			boleto = boletoDao.create(boleto);
			boletoDao.commitTransaction();
			
		} catch (Throwable e) {
	//		httpGoApi.geraLog("BoletoFactory || cadastraBoletoTaxaDeSegundaViaDeCarteira", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return boleto.getId();
	}
}
