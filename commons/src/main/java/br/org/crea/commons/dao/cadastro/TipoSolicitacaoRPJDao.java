package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.TipoSolicitacaoRPJ;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless	
public class TipoSolicitacaoRPJDao extends GenericDao<TipoSolicitacaoRPJ, Serializable>  {

	@Inject HttpClientGoApi httpGoApi;
	
	
	public TipoSolicitacaoRPJDao() {
		super(TipoSolicitacaoRPJ.class);
	}
	
}
