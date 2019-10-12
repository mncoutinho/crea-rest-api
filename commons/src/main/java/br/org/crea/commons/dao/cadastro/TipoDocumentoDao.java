package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.TipoDocumento;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class TipoDocumentoDao extends GenericDao<TipoDocumento, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public TipoDocumentoDao() {
		super(TipoDocumento.class);
	}
	
	
	public List<TipoDocumento> porModulo(Long modulo) {
		
		List<TipoDocumento> listModel = new ArrayList<TipoDocumento>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T FROM TipoDocumento T ");
		sql.append("	WHERE T.modulo = :modulo ");
		sql.append("   	ORDER BY T.descricao");

		try {
			TypedQuery<TipoDocumento> query = em.createQuery(sql.toString(), TipoDocumento.class);
			query.setParameter("modulo", modulo);
			
			listModel = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("TipoDocumentolDao || getAtivosInativos", StringUtil.convertObjectToJson(modulo), e);
		}
		
		return listModel;
	
	}


	public List<TipoDocumento> getAllTipoDocumentos() {
		
		List<TipoDocumento> listModel = new ArrayList<TipoDocumento>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T FROM TipoDocumento T ");
		sql.append("   	ORDER BY T.descricao");

		try {
			TypedQuery<TipoDocumento> query = em.createQuery(sql.toString(), TipoDocumento.class);
			
			listModel = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("TipoDocumentolDao || getAllTipoDocumentos", StringUtil.convertObjectToJson("-- SEM PARAMETRO --"), e);
		}
		
		return listModel;
	}



	
	
}
