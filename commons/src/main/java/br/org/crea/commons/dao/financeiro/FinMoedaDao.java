package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.FinMoeda;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class FinMoedaDao extends GenericDao<FinMoeda, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public FinMoedaDao() {
		super(FinMoeda.class);
	}

	public FinMoeda getMoedaBy(Date data) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT M FROM FinMoeda M                                    ");
		sql.append("	WHERE M.vigenciaInicio <= :data                          ");
		sql.append("	  AND (M.vigenciaFim >= :data OR M.vigenciaFim IS NULL)  ");

		try {
			TypedQuery<FinMoeda> query = em.createQuery(sql.toString(), FinMoeda.class);
			query.setParameter("data", data);
			
			return (FinMoeda) query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinMoedaDao || getMoedaBy", StringUtil.convertObjectToJson(data), e);
		}

		return null;
	}
}
