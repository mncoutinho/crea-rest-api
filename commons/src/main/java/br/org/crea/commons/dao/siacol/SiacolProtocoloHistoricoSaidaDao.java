package br.org.crea.commons.dao.siacol;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.SiacolProtocoloHistoricoSaida;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class SiacolProtocoloHistoricoSaidaDao extends GenericDao<SiacolProtocoloHistoricoSaida, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public SiacolProtocoloHistoricoSaidaDao() {
		super(SiacolProtocoloHistoricoSaida.class);
	}

	public SiacolProtocoloHistoricoSaida getHistorioBy(Long idDepartamentoDestino, Long numeroProtocolo) {
		
		SiacolProtocoloHistoricoSaida siacolProtocoloHistoricoSaida = new SiacolProtocoloHistoricoSaida();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PH FROM SiacolProtocoloHistoricoSaida PH ");
		sql.append("	WHERE PH.departamento.id = :idDepartamentoDestino ");
		sql.append("	AND PH.numeroProtocolo = :numeroProtocolo ");

		try {

			TypedQuery<SiacolProtocoloHistoricoSaida> query = em.createQuery(sql.toString(), SiacolProtocoloHistoricoSaida.class);
			query.setParameter("idDepartamentoDestino", idDepartamentoDestino);
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setMaxResults(1);

			siacolProtocoloHistoricoSaida = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolProtocoloHistoricoSaidaDao || getHistorioBy", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}

		return siacolProtocoloHistoricoSaida;
	}

}
