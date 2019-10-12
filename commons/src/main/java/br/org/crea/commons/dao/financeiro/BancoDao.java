package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.Banco;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class BancoDao extends GenericDao<Banco, Serializable> {

	@Inject private HttpClientGoApi httpGoApi;
	
	public BancoDao() {
		super(Banco.class);
	}
	

}
