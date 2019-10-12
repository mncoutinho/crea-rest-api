package br.org.crea.commons.dao.cadastro.leigo;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.pessoa.LeigoPF;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class LeigoPFDao extends GenericDao<LeigoPF, Serializable>{
	
	@Inject HttpClientGoApi httpGoApi;
	
	public LeigoPFDao(){
		super(LeigoPF.class);
	}	
}
