package br.org.crea.commons.dao.cadastro.pessoa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.HomePage;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class HomePageDao extends GenericDao<HomePage, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public HomePageDao() {
		super(HomePage.class);
	}
	
	public List<HomePage> getHomePagesByPessoa(Long idPessoa){
		List<HomePage> homepages = new ArrayList<HomePage>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT h FROM HomePage h ");
		sql.append("WHERE  h.pessoa.id = :idPessoa ");
		
		try{
			TypedQuery<HomePage> query = em.createQuery(sql.toString(), HomePage.class);
			query.setParameter("idPessoa", idPessoa);
			
			homepages = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("HomePageDao || Get HomePages by Pessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}	
		
		return homepages;
	}
	

}
