package br.org.crea.commons.dao.cadastro.profissional;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.CarteiraFila;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class CarteiraFilaDao extends GenericDao<CarteiraFila, Serializable> {
	
	@Inject
	HttpClientGoApi httpGoApi;

	public CarteiraFilaDao() {
		super(CarteiraFila.class);
	}

	

}
