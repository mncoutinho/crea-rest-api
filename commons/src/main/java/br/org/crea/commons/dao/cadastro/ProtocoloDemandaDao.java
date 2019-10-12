package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.protocolo.ProtocoloDemanda;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ProtocoloDemandaDao extends GenericDao<ProtocoloDemanda, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public ProtocoloDemandaDao() {
		super(ProtocoloDemanda.class);
	}

	public Long getNumeroProtocoloExigencia(Long numeroProtocolo) {
		
		ProtocoloDemanda protocoloDemanda = new ProtocoloDemanda();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PD FROM ProtocoloDemanda PD ");
		sql.append("     WHERE PD.protocoloFilho LIKE '" + numeroProtocolo + "' ");
		sql.append("     AND PD.tipoDemanda.id <> 3 ");

		try {

			TypedQuery<ProtocoloDemanda> query = em.createQuery(sql.toString(), ProtocoloDemanda.class);

			protocoloDemanda = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDemandaDao || getNumeroProtocoloExigencia", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return protocoloDemanda.getProtocoloPai();
	}

	public Long getIdProtocoloRecurso(Long numeroProtocolo) {
		
		ProtocoloDemanda protocoloDemanda = new ProtocoloDemanda();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PD FROM ProtocoloDemanda PD ");
		sql.append("     WHERE PD.protocoloFilho LIKE '" + numeroProtocolo + "' ");
		sql.append("     AND PD.tipoDemanda.id = 3 ");

		try {

			TypedQuery<ProtocoloDemanda> query = em.createQuery(sql.toString(), ProtocoloDemanda.class);

			protocoloDemanda = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDemandaDao || getNumeroProtocoloExigencia", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return protocoloDemanda.getProtocoloPai();
	}
	
}
