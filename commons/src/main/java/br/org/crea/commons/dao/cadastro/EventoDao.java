package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.Evento;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class EventoDao extends GenericDao<Evento, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;
	
	public EventoDao() {
		super(Evento.class);
	}

	public boolean podeDeletar(Long idEvento) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(*) FROM CAD_HISTORICOS ");
		sql.append("	WHERE FK_CODIGO_EVENTOS = :idEvento ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idEvento", idEvento);

			BigDecimal resp = (BigDecimal) query.getSingleResult();
			
			return resp.intValueExact() == 0 ? true : false;

		} catch (Throwable e) {
			httpGoApi.geraLog("EventoDao || podeDeletar", StringUtil.convertObjectToJson(idEvento), e);
		}
		return false;
	}

	public List<Evento> getEventoByAssuntoProtocolo(Long idAssuntoProtocolo) {
		
		List<Evento> listEvento = new ArrayList<Evento>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT E FROM Evento E ");
		sql.append("	WHERE E.assunto.id = :idAssuntoProtocolo ");

		try {
			TypedQuery<Evento> query = em.createQuery(sql.toString(), Evento.class);
			query.setParameter("idAssuntoProtocolo", idAssuntoProtocolo);
			
			listEvento = query.getResultList();
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EventoDao || getEventoByAssuntoProtocolo", StringUtil.convertObjectToJson(idAssuntoProtocolo), e);
		}
		return listEvento;
	}
		
}
