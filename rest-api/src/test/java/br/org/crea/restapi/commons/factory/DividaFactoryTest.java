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
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.dao.financeiro.RlDividaBoletoDao;
import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.financeiro.Boleto;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.financeiro.NaturezaDivida;
import br.org.crea.commons.models.financeiro.RlDividaBoleto;
import br.org.crea.commons.models.financeiro.StatusDivida;
import br.org.crea.commons.models.financeiro.enuns.FinNaturezaEnum;
import br.org.crea.commons.models.financeiro.enuns.StatusDividaEnum;
import br.org.crea.commons.util.StringUtil;

public class DividaFactoryTest {

	private EntityManagerFactory emf;
	
	private EntityManager em;
	private FinDividaDao dividaDao;
	private RlDividaBoletoDao rlDividaBoletoDao;
	private BoletoDao boletoDao;
	
	@Before
	public void setup() {
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();

		 dividaDao = new FinDividaDao();
		 rlDividaBoletoDao = new RlDividaBoletoDao();
		 boletoDao = new BoletoDao();
		 boletoDao.setEntityManager(em);
		 dividaDao.setEntityManager(em);
		 rlDividaBoletoDao.setEntityManager(em);
	}
	
	@After
	public void finishTest() {
		em.close();
		emf.close();
	}
	
	@Test
	public void cadastraDividaTaxaDeSegundaViaFactoryTest () {
		Boleto boleto = boletoDao.getBy(23638016962931764L);
		Long idDivida = cadastrarDividaTaxaDeSegundaViaDeCarteira(boleto);
		System.out.println("IdDivida: "+ idDivida);
	}
	
	
	public Long cadastrarDividaTaxaDeSegundaViaDeCarteira(Boleto boleto) {
		
		FinDivida divida = new FinDivida();
		
		try {
			
			NaturezaDivida natureza = new NaturezaDivida();
			natureza.setId(FinNaturezaEnum.EXPEDICAO_CARTEIRA.getId());
			divida.setNatureza(natureza);			
			divida.setData(new Date());
			divida.setDataVencimento(new Date());			
			divida.setNossoNumero(boleto.getNossoNumero());
			divida.setObservacao("Taxa " + FinNaturezaEnum.EXPEDICAO_CARTEIRA.getDescricao());
			
			Pessoa pessoa = new Pessoa();
			pessoa.setId(boleto.getIdPessoa());
			divida.setPessoa(pessoa);
			divida.setTipoPessoa(TipoPessoa.PESSOAFISICA);
			divida.setServicoExecutado(false);
			divida.setParcela(0);			
			divida.setValorOriginal(boleto.getValorAtual());
			divida.setValorAtual(boleto.getValorAtual());
			divida.setJuros(new BigDecimal(0));
			divida.setMulta(new BigDecimal(0));
			divida.setHonorarios(new BigDecimal(0));
			
			StatusDivida statusDivida = new StatusDivida();
			statusDivida.setId(StatusDividaEnum.A_VENCER.getId());
			divida.setStatus(statusDivida);
						
			dividaDao.createTransaction();
			divida = dividaDao.create(divida);
			dividaDao.commitTransaction();
			
			RlDividaBoleto rlDividaBoleto = new RlDividaBoleto();
			rlDividaBoleto.setBoleto(boleto);
			rlDividaBoleto.setDivida(divida);
			rlDividaBoletoDao.createTransaction();
			rlDividaBoletoDao.create(rlDividaBoleto);
			rlDividaBoletoDao.commitTransaction();
								  
		} catch (Throwable e) {
//			httpGoApi.geraLog("DividaFactory || cadastrarDividaTaxaDeSegundaViaDeCarteira", StringUtil.convertObjectToJson(boleto), e);
		}
	  
		return divida.getId();
	}
}
