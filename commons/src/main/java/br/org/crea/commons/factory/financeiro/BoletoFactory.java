package br.org.crea.commons.factory.financeiro;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.financeiro.BoletoDao;
import br.org.crea.commons.dao.financeiro.FinGeradorNossoNumeroDao;
import br.org.crea.commons.dao.financeiro.FinPrecoTaxaDao;
import br.org.crea.commons.models.financeiro.Boleto;
import br.org.crea.commons.models.financeiro.enuns.FinNaturezaEnum;
import br.org.crea.commons.models.financeiro.enuns.StatusBoletoEnum;
import br.org.crea.commons.models.financeiro.enuns.StatusEmissaoBoletoEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class BoletoFactory {

	@Inject private BoletoDao boletoDao;
	
	@Inject private FinGeradorNossoNumeroDao finGeradorNossoNumeroDao;
	
	@Inject private FinPrecoTaxaDao finPrecoTaxaDao;
	
	@Inject private DividaFactory dividaFactory;
	
	@Inject private HttpClientGoApi httpGoApi;
	
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

			boleto = boletoDao.create(boleto);
			
			dividaFactory.cadastrarDividaTaxaDeSegundaViaDeCarteira(boleto);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("BoletoFactory || cadastraBoletoTaxaDeSegundaViaDeCarteira", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return boleto.getId();
	}
}
