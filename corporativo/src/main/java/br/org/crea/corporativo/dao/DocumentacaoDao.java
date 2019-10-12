package br.org.crea.corporativo.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.Documentacao;
import br.org.crea.commons.service.HttpClientGoApi;


public class DocumentacaoDao extends GenericDao<Documentacao, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public DocumentacaoDao() {
		super(Documentacao.class);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Documentacao> getDocumentacaoDisponiveis(Long idAssunto){
		
		List<Documentacao> documentacao = new ArrayList<Documentacao>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_DOCUMENTACAO CD " );
		sql.append("WHERE Not Exists ( SELECT FK_ID_PRT_ASSUNTOS " ); 
		sql.append("	      FROM CAD_RL_ASSUNTOS_DOCUMENTACAO CRA WHERE CRA.FK_ID_PRT_ASSUNTOS = :idAssunto AND " );
		sql.append("	      CRA.FK_ID_DOCUMENTACAO = CD.ID  ) " ); 
		sql.append("	ORDER BY NOME " );


		try {
			Query query = em.createNativeQuery(sql.toString(), Documentacao.class);
			query.setParameter("idAssunto", idAssunto);

			documentacao = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("Documentacao || getDocumentacaoDisponiveis", "Sem Parametro", e);

		}
		
		return documentacao.isEmpty() ? null : documentacao;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Documentacao> getDocumentacaoIndisponiveis(Long idAssunto){
		
		List<Documentacao> documentacao = new ArrayList<Documentacao>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM CAD_DOCUMENTACAO CD " );
		sql.append("WHERE Exists ( SELECT FK_ID_PRT_ASSUNTOS " ); 
		sql.append("	      FROM CAD_RL_ASSUNTOS_DOCUMENTACAO CRA WHERE CRA.FK_ID_PRT_ASSUNTOS = :idAssunto AND " );
		sql.append("	      CRA.FK_ID_DOCUMENTACAO = CD.ID  ) " ); 
		sql.append("	ORDER BY NOME " );


		try {
			Query query = em.createNativeQuery(sql.toString(), Documentacao.class);
			query.setParameter("idAssunto", idAssunto);

			documentacao = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("Documentacao || getDocumentacaoDisponiveis", "Sem Parametro", e);

		}
		
		return documentacao.isEmpty() ? null : documentacao;
	}
	
	
	
}
