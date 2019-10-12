package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.Acoes;
import br.org.crea.commons.service.HttpClientGoApi;


@Stateless
public class AcoesDao extends GenericDao<Acoes, Serializable> {
	
	@Inject
	HttpClientGoApi httpGoApi;

	public AcoesDao() {
		super(Acoes.class);
	}
	
	

}
