package br.org.crea.corporativo.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.Taxa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;


public class TaxaDao extends GenericDao<Taxa, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public TaxaDao() {
		super(Taxa.class);
	}
	
	public Taxa getTaxaBy(Long idTaxa){
		
		Taxa taxa = new Taxa();
		
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT T FROM Taxa T ");
		sql.append("	WHERE T.id = :idTaxa AND ");
		sql.append("	T.ativo = 1 ");

		try {
			TypedQuery<Taxa> query = em.createQuery(sql.toString(), Taxa.class);
			query.setParameter("idTaxa", idTaxa);
			
			taxa = (Taxa) query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("TaxaDao || getTaxaBy", StringUtil.convertObjectToJson(idTaxa), e);

		}
		
		return taxa;
	}
	
	public List<Taxa> getTaxas(){
		
		List<Taxa> taxas = new ArrayList<Taxa>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT T FROM Taxa T ");
		sql.append("	WHERE T.exercicio = TO_CHAR(SYSDATE, 'YYYY') AND ");
		sql.append("	T.ativo = 1 ");

		try {
			TypedQuery<Taxa> query = em.createQuery(sql.toString(), Taxa.class);
			
			taxas = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("TaxaDao || getTaxas", "sem parametro",  e);

		}
		
		return taxas;
	}

	
}
