package br.org.crea.commons.dao.protocolo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.RlAssuntosSiacol;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class RlAssuntosDao extends GenericDao<RlAssuntosSiacol, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public RlAssuntosDao() {
		super(RlAssuntosSiacol.class);
	}

	public List<RlAssuntosSiacol> getByAssuntoSiacol(Long codigo) {
		
		List<RlAssuntosSiacol> listaRl = new ArrayList<RlAssuntosSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM RlAssuntosSiacol R ");
		sql.append("     WHERE R.assuntoSiacol.id = :id ");
		sql.append("     ORDER BY R.assunto.codigo ");

		try {

			TypedQuery<RlAssuntosSiacol> query = em.createQuery(sql.toString(), RlAssuntosSiacol.class);
			query.setParameter("id", codigo);
			listaRl = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlAssuntosDao || getByAssuntoSiacol", "SEM PARAMETRO", e);
		}
		return listaRl;
	}
	
	public Boolean deleteHabilidadeByAssunto(Long codigo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM SIACOL_HABILIDADE_PESSOA R ");
		sql.append("     WHERE R.FK_ASSUNTO = :id ");
		
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", codigo);
			query.executeUpdate();

		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlAssuntosDao || deleteHabilidadeByAssunto", "SEM PARAMETRO", e);
		}
		return true ;
	}
	
	public Boolean deleteByAssuntoSiacol(Long codigo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM SIACOL_RL_ASSUNTOS R ");
		sql.append("     WHERE R.FK_ASSUNTO_SIACOL = :id ");
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("DELETE FROM SIACOL_HABILIDADE_PESSOA R ");
		sql2.append("     WHERE R.FK_ASSUNTO_SIACOL = :id ");
		
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", codigo);
			query.executeUpdate();
			
			Query query2 = em.createNativeQuery(sql2.toString());
			query2.setParameter("id", codigo);
			query2.executeUpdate();


		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlAssuntosDao || getByAssuntoSiacol", "SEM PARAMETRO", e);
		}
		return true ;
	}
	
	@Transactional()
	public Boolean deleteByAssuntoProtocolo(Long codigo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM SIACOL_RL_ASSUNTOS R ");
		sql.append("     WHERE R.FK_PRT_ASSUNTO = :id ");
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("DELETE FROM SIACOL_HABILIDADE_PESSOA R ");
		sql2.append("     WHERE R.FK_ASSUNTO = :id ");
		
		
		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", codigo);
			query.executeUpdate();
			
			Query query2 = em.createNativeQuery(sql2.toString());
			query2.setParameter("id", codigo);
			query2.executeUpdate();

		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlAssuntosDao || deleteByAssuntoProtocolo", "SEM PARAMETRO", e);
		}
		return true ;
	}
			

}
