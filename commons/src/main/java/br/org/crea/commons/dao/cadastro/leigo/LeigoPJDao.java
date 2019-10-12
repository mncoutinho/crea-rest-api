package br.org.crea.commons.dao.cadastro.leigo;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.pessoa.LeigoPJ;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class LeigoPJDao extends GenericDao<LeigoPJ, Serializable>{
	
	@Inject HttpClientGoApi httpGoApi;
	
	public LeigoPJDao(){
		super(LeigoPJ.class);
	}	
}
