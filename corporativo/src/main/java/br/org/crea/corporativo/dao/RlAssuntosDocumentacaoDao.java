package br.org.crea.corporativo.dao;

import java.io.Serializable;

import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.RlAssuntosDocumentacao;
import br.org.crea.commons.service.HttpClientGoApi;


public class RlAssuntosDocumentacaoDao extends GenericDao<RlAssuntosDocumentacao, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public RlAssuntosDocumentacaoDao() {
		super(RlAssuntosDocumentacao.class);
	}
	
	public RlAssuntosDocumentacao getDocumentacao(){
		
		RlAssuntosDocumentacao rlAssuntosDocumentacao = new RlAssuntosDocumentacao();
		
//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT * FROM PRT_ASSUNTOS A");
//		sql.append("	WHERE A.id = :id ");
//
//		try {
//			TypedQuery<Documentacao> query = em.createQuery(sql.toString(), Documentacao.class);
////			Query query = em.createQuery(sql.toString(), Documentacao.class);
//			
//			documentacao = (Documentacao) query.getSingleResult();
//		} catch (Throwable e) {
//			httpGoApi.geraLog("Documentacao || getDocumentacao", "Sem Parametro", e);
//
//		}
		
		return rlAssuntosDocumentacao;
	}
	
	
	
}
