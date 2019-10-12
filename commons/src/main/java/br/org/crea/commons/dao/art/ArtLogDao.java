package br.org.crea.commons.dao.art;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.art.ArtLog;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class ArtLogDao extends GenericDao<ArtLog, Serializable> {
	
	@Inject
	HttpClientGoApi httpGoApi;

	public ArtLogDao() {
		super(ArtLog.class);
	}
	
	
}