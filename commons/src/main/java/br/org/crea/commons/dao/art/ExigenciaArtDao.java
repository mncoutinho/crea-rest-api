package br.org.crea.commons.dao.art;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.art.ExigenciaArt;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ExigenciaArtDao extends GenericDao<ExigenciaArt, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public ExigenciaArtDao() {
		super(ExigenciaArt.class);
	}

	public List<ExigenciaArt> getExigenciasPorArt(String numeroArt) {
		
		List<ExigenciaArt> listExigencia = new ArrayList<ExigenciaArt>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT E FROM ExigenciaArt E  ");
		sql.append("  WHERE E.art.numero = :numeroArt ");

		try {
			TypedQuery<ExigenciaArt> query = em.createQuery(sql.toString(), ExigenciaArt.class);
			query.setParameter("numeroArt", numeroArt);

			listExigencia = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ExigenciaArtDao || getExigenciasPorArt", StringUtil.convertObjectToJson(numeroArt), e);
		}

		return listExigencia;
	}

	public void deletaExigenciasPor(String idContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM ART_EXIGENCIA_ART E   ");
		sql.append("  WHERE E.FK_CONTRATO = :numeroArt ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);

			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("ExigenciaArtDao || deletaExigenciasPor", StringUtil.convertObjectToJson(idContrato), e);
		}
	}
	
}
