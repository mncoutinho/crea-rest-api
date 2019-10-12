package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.FinPrecoTaxa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class FinPrecoTaxaDao extends GenericDao<FinPrecoTaxa, Serializable>{
	
	@Inject HttpClientGoApi httpGoApi;
	
	public FinPrecoTaxaDao(){
		super(FinPrecoTaxa.class);
	}

	public BigDecimal getValorTaxaPorNaturezaEExercicio(Long idNatureza, int exercicio) {
	
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT T.VALOR FROM FIN_PRECO_TAXA T       ");
		sql.append("  WHERE T.FK_CODIGO_NATUREZA = :idNatureza  ");
		sql.append("    AND T.EXERCICIO = :exercicio            ");
				
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idNatureza", idNatureza);
			query.setParameter("exercicio", exercicio);
			
			return (BigDecimal) query.getSingleResult();
		} catch (Exception e) {
			httpGoApi.geraLog("FinPrecoTaxaDao || getValorTaxaPorNaturezaEExercicio", StringUtil.convertObjectToJson(idNatureza + " - " + exercicio), e);
		}
		return null;

	}

}
