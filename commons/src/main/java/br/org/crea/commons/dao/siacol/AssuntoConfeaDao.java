package br.org.crea.commons.dao.siacol;

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
import br.org.crea.commons.models.siacol.AssuntoConfea;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class AssuntoConfeaDao extends GenericDao<AssuntoConfea, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public AssuntoConfeaDao() {
		super(AssuntoConfea.class);
	}

	 public AssuntoConfea verificaCodigo(Long codigo) {
		 
	 AssuntoConfea assunto = new AssuntoConfea();
	
	 StringBuilder sql = new StringBuilder();
	 sql.append("SELECT A FROM AssuntoConfea A ");
	 sql.append("     WHERE A.codigo = :codigo ");
	
	 try {
	 TypedQuery<AssuntoConfea> query = em.createQuery(sql.toString(),AssuntoConfea.class);
	 query.setParameter("codigo", codigo);
	
	 assunto = query.getSingleResult();
	
	 } catch (NoResultException e ){
		 return null;
	 } catch (Throwable e) {
	 httpGoApi.geraLog("AssuntoConfeaDao || verificaCodigo",StringUtil.convertObjectToJson(codigo),	 e);
	 }
	 	return assunto;
	 }

	
	public List<AssuntoConfea> getAllAssuntos() {
		List<AssuntoConfea> listaAssunto = new ArrayList<AssuntoConfea>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AssuntoConfea A ");
		sql.append("     WHERE A.ativo = 1 ");

		try {

			TypedQuery<AssuntoConfea> query = em.createQuery(sql.toString(), AssuntoConfea.class);

			listaAssunto = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoConfeaDao || getAllAssuntos", "SEM PARAMETRO", e);
		}
		return listaAssunto;
	}

	@Transactional()
	public Boolean deleteByAssuntoConfea(Long codigo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE SIACOL_ASSUNTO S ");
		sql.append("	SET S.FK_ASSUNTO_CONFEA = null ");
		sql.append("    WHERE S.FK_ASSUNTO_CONFEA = :id ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", codigo);
			query.executeUpdate();

		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoConfeaDao || deleteByAssuntoConfea", "SEM PARAMETRO", e);
		}
		return true ;
	}

	


}
