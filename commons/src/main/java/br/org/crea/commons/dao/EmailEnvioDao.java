package br.org.crea.commons.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.models.commons.EmailEnvio;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class EmailEnvioDao extends GenericDao<EmailEnvio, Serializable> {

	public EmailEnvioDao() {
		super(EmailEnvio.class);
	}
	
	@Inject
	HttpClientGoApi httpGoApi;
}


