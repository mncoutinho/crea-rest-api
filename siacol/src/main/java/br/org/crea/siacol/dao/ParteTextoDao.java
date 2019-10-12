package br.org.crea.siacol.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.ParteTexto;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class ParteTextoDao extends GenericDao<ParteTexto, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;
	
	public ParteTextoDao() {
		super(ParteTexto.class);
	}

	public Long getProximoNumeroOrdem() {
		
		Long proximoNumero = 0L;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(PT.ordem) FROM ParteTexto PT ");
		sql.append("     WHERE PT.ativo = 1 ");

		try {

			TypedQuery<Long> query = em.createQuery(sql.toString(), Long.class);

			proximoNumero = query.getSingleResult();

		} catch (NoResultException e) {
			return 0L;
		} catch (Throwable e) {
			httpGoApi.geraLog("ParteTextoDao || getProximoNumeroOrdem", "SEM PARAMETRO", e);
		}
		return (proximoNumero +1);
	}
	
}