package br.org.crea.commons.dao.fiscalizacao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.fiscalizacao.ContratoInativado;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class ContratoInativadoDao extends GenericDao<ContratoInativado, Serializable>{

	@Inject HttpClientGoApi httpGoApi;

	@PersistenceContext(unitName = "dscrea")
	protected EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public ContratoInativadoDao(){
		super(ContratoInativado.class);
	}
}
