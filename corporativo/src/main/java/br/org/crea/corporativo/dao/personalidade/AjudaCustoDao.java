package br.org.crea.corporativo.dao.personalidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.personalidade.entity.AjudaCusto;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class AjudaCustoDao extends GenericDao<AjudaCusto, Serializable>  {
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	public AjudaCustoDao() {
		super(AjudaCusto.class);

	}
	
	public List<AjudaCusto> getAll() {
		List<AjudaCusto> acusto = new ArrayList<AjudaCusto>();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT c FROM AjudaCusto c ORDER BY c.localidade.descricao ");

		try {
			TypedQuery<AjudaCusto> query = em.createQuery(sql.toString(), AjudaCusto.class);

			acusto = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("AjudaCustoDao || getAll", "sem parametro",  e);

		}

		return acusto;
	}

	
	public AjudaCusto getByLocalidade(Long localidade){
		
		AjudaCusto acusto = new AjudaCusto();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT A FROM AjudaCusto A ");
		sql.append("	WHERE A.localidade.id = :localidade " );


		try {
			TypedQuery<AjudaCusto> query = em.createQuery(sql.toString(), AjudaCusto.class);
			query.setParameter("localidade", localidade);
			
		    acusto = query.getSingleResult();

		} catch (Throwable e) {
			httpGoApi.geraLog("AjudaCustoDaoDao || getAll", "sem parametro",  e);

		}
		
		return acusto;
	}

}
