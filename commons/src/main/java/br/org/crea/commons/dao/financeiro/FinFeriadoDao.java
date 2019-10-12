package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.FinFeriado;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class FinFeriadoDao extends GenericDao<FinFeriado, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public FinFeriadoDao() {
		super(FinFeriado.class);
	}

	public boolean verificaSeEhFeriado(Date data) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(F.CODIGO) FROM FIN_FERIADO F   ");
		sql.append(" WHERE (F.DATA = :data OR F.DATA = :diaMes)  ");
		sql.append("	AND F.ATIVO = 1                          ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("data", DateUtils.format(data, DateUtils.DD_MM_YYYY));
			query.setParameter("diaMes", DateUtils.format(data, DateUtils.DD_MM));
			
			BigDecimal resultado = (BigDecimal) query.getSingleResult();
			
			return resultado.compareTo(new BigDecimal("0")) > 0;
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinFeriadoDao || verificaSeEhFeriado", StringUtil.convertObjectToJson(data), e);
		}

		return false;
	}
}
