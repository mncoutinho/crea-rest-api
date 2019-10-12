package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.Boleto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class BoletoDao extends GenericDao<Boleto, Serializable> {

	@Inject private HttpClientGoApi httpGoApi;
	
	public BoletoDao() {
		super(Boleto.class);
	}
	
	public Long getConvenioByNatureza(Long idNatureza) {
		
		Long numeroConvenio = null;
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT N.FK_CODIGO_CONVENIO                       ");
		sql.append("  FROM FIN_NATUREZA_CONF N                                  ");
		sql.append("  JOIN FIN_CONVENIO C ON (N.FK_CODIGO_CONVENIO = C.CODIGO)  ");
		sql.append("  WHERE N.FK_CODIGO_NATUREZA = :idNatureza                  ");
		sql.append("    AND C.ATIVO = 1                                         ");
		sql.append("	AND N.ATIVO = 1                                         ");
				
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idNatureza", idNatureza);
			
			numeroConvenio = Long.parseLong(query.getSingleResult().toString());
								
		} catch (Throwable e) {
			httpGoApi.geraLog("BoletoDao || getConvenioByNatureza", StringUtil.convertObjectToJson(idNatureza), e);
		}
		
		return numeroConvenio;
		
	}
	

	public Boleto getBoletoByNossoNumero(String nossoNumero) {
		
		Boleto boleto = new Boleto();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT B FROM Boleto B               ");
		sql.append("  WHERE B.nossoNumero = :nossoNumero  ");
				
		try {
			TypedQuery<Boleto> query = em.createQuery(sql.toString(), Boleto.class);
			query.setParameter("nossoNumero", nossoNumero);
			
			boleto = query.getSingleResult();
								
		} catch (Throwable e) {
			httpGoApi.geraLog("BoletoDao || getBoletoByNossoNumero", StringUtil.convertObjectToJson(nossoNumero), e);
		}
		
		return boleto;
		
	}

}
