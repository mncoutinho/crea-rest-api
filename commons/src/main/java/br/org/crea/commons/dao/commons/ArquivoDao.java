package br.org.crea.commons.dao.commons;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArquivoDao extends GenericDao<Arquivo, Serializable>{
	
	@Inject
	HttpClientGoApi httpGoApi;

	public ArquivoDao() {
		super(Arquivo.class);
	}
	
	public Arquivo getArquivoBy(String nome) {
		
		Arquivo arquivo = new Arquivo();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Arquivo A ");
		sql.append("	WHERE A.nomeStorage = :nome ");

		try {
			TypedQuery<Arquivo> query = em.createQuery(sql.toString(), Arquivo.class);
			query.setParameter("nome", nome);
			
			arquivo =  query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("ArquivoDao || getArquivoBy", StringUtil.convertObjectToJson(nome), e);
		}
		
		return arquivo;
	
	}
	
	public boolean validaArquivoExistente(String nome) {
		Query query = em.createQuery(" SELECT A FROM Arquivo A "
				+ " WHERE A.nomeOriginal = ?");
		query.setParameter(1, nome);
		return query.getResultList().isEmpty();
	}

}
