package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.RlModalidadeDepartamento;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class RlModalidadeDepartamentoDao extends GenericDao<RlModalidadeDepartamento, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public RlModalidadeDepartamentoDao() {
		super(RlModalidadeDepartamento.class);
	}
	

}
