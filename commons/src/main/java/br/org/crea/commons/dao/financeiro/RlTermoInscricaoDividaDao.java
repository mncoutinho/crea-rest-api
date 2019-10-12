package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.financeiro.FinTermosInscricao;
import br.org.crea.commons.models.financeiro.RlTermoInscricaoDivida;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RlTermoInscricaoDividaDao extends GenericDao<RlTermoInscricaoDivida, Serializable>{

	@Inject
	HttpClientGoApi httpGoApi;

	public RlTermoInscricaoDividaDao() {
		super(RlTermoInscricaoDivida.class);
	}
	
	public void cadastraTermoInscricaoDivida(FinDivida divida, FinTermosInscricao termoInscricao) {
		
		try {
			
			RlTermoInscricaoDivida rlTermoDivida = new RlTermoInscricaoDivida();
			rlTermoDivida.setDivida(divida);
			rlTermoDivida.setTermoInscricao(termoInscricao);
			create(rlTermoDivida);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("RlTermoInscricaoDividaDao || cadastraTermoInscricaoDivida", StringUtil.convertObjectToJson(divida), e);
		}
	}
	
}
