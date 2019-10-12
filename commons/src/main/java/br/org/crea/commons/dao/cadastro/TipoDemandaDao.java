package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.atendimento.OuvidoriaTipoDemanda;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class TipoDemandaDao extends GenericDao<OuvidoriaTipoDemanda, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public TipoDemandaDao() {
		super(OuvidoriaTipoDemanda.class);
	}	
	
}
