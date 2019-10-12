package br.org.crea.corporativo.dao;

import java.io.Serializable;

import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.Denuncia;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class DenunciaDao extends GenericDao<Denuncia, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public DenunciaDao() {
		super(Denuncia.class);
	}

	public Denuncia cadastroDenunciaAnonima(Denuncia denuncia) {

		try {
			
			denuncia = create(denuncia);			
//			return create(denuncia);

		} catch (Throwable e) {
			httpGoApi.geraLog("DenunciaDao || cadastroDenunciaAnonima",	StringUtil.convertObjectToJson(denuncia), e);
		}
		
		return denuncia;

	}

}
