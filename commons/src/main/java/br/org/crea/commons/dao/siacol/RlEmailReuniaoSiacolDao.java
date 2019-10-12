package br.org.crea.commons.dao.siacol;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.RlEmailReuniaoSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RlEmailReuniaoSiacolDao extends GenericDao<RlEmailReuniaoSiacol, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public RlEmailReuniaoSiacolDao() {
		super(RlEmailReuniaoSiacol.class);
	}


	public RlEmailReuniaoSiacol getEmailsPor(Long codigoReuniao, Long idEvento) {

		RlEmailReuniaoSiacol rlEmailReuniao = new RlEmailReuniaoSiacol();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlEmailReuniaoSiacol RL ");
		sql.append("	WHERE RL.reuniao.id = :codigoReuniao ");
		sql.append("	AND RL.emailEnvio.eventoEmail.id = :idEvento ");

		try {

			TypedQuery<RlEmailReuniaoSiacol> query = em.createQuery(sql.toString(), RlEmailReuniaoSiacol.class);
			query.setParameter("codigoReuniao", codigoReuniao);
			query.setParameter("idEvento", idEvento);
			query.setMaxResults(1);

			rlEmailReuniao = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlEmailReuniaoSiacol || getEmailsPor", StringUtil.convertObjectToJson(codigoReuniao), e);
		}

		return rlEmailReuniao;
	}
}
