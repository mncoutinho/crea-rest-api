package br.org.crea.commons.factory.financeiro;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

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
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class DividaFactory {

	@Inject private FinDividaDao dividaDao;
	
	@Inject private RlDividaBoletoDao rlDividaBoletoDao;
	
	@Inject private HttpClientGoApi httpGoApi;
	
	public void cadastrarDividaTaxaDeSegundaViaDeCarteira(Boleto boleto) {
		
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
						
			divida = dividaDao.create(divida);
			
			RlDividaBoleto rlDividaBoleto = new RlDividaBoleto();
			rlDividaBoleto.setBoleto(boleto);
			rlDividaBoleto.setDivida(divida);
			rlDividaBoletoDao.create(rlDividaBoleto);
								  
		} catch (Throwable e) {
			httpGoApi.geraLog("DividaFactory || cadastrarDividaTaxaDeSegundaViaDeCarteira", StringUtil.convertObjectToJson(boleto), e);
		}
	  
	}

}
