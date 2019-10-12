package br.org.crea.commons.dao.siacol;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.SiacolProtocoloExigencia;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class SiacolProtocoloExigenciaDao extends GenericDao<SiacolProtocoloExigencia, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public SiacolProtocoloExigenciaDao() {
		super(SiacolProtocoloExigencia.class);
	}


	public SiacolProtocoloExigencia buscaExigenciaByIdProtocolo(Long idProtocolo) {

		SiacolProtocoloExigencia exigencia = new SiacolProtocoloExigencia();
		
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT SPE FROM SiacolProtocoloExigencia SPE ");
		sql.append("	WHERE SPE.protocolo.id = :idProtocolo ");
		sql.append("	AND SPE.dataFim is null ");

		try {
			TypedQuery<SiacolProtocoloExigencia> query = em.createQuery(sql.toString(), SiacolProtocoloExigencia.class);
			query.setParameter("idProtocolo", idProtocolo);
			
			exigencia = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolProtocoloExigenciaDao || buscaExigenciaByIdProtocolo", StringUtil.convertObjectToJson(idProtocolo), e);
		}
		return exigencia;
	}



}
