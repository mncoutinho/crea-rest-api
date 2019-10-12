package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.RlDividaBoleto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RlDividaBoletoDao extends GenericDao<RlDividaBoleto, Serializable>{

	@Inject
	HttpClientGoApi httpGoApi;

	public RlDividaBoletoDao() {
		super(RlDividaBoleto.class);
	}

	public List<RlDividaBoleto> getByIdBoleto(Long idBoleto) {
		
		List<RlDividaBoleto> list = new ArrayList<RlDividaBoleto>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT RL FROM RlDividaBoleto RL  ");
		sql.append("  WHERE RL.boleto.id = :idBoleto   ");
		sql.append("    AND RL.divida.id IS NOT NULL   ");

		try {
			TypedQuery<RlDividaBoleto> query = em.createQuery(sql.toString(), RlDividaBoleto.class);
			query.setParameter("idBoleto", idBoleto);
			
			list = query.getResultList(); 
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDevolucaoDao || getByIdBoleto", StringUtil.convertObjectToJson(idBoleto), e);
		}

		return list;
	}
	
}
