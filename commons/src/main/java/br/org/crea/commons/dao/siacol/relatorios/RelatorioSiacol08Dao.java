package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;

import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.service.HttpClientGoApi;

public class RelatorioSiacol08Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	
	public RelatorioSiacol08Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
}
