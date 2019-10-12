package br.org.crea.siacol.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.org.crea.commons.service.HttpClientGoApi;


@Stateless
public class AnaliseProtocoloSiacolDao {
	
	@Inject HttpClientGoApi httpGoApi;

	@PersistenceContext(unitName = "dscrea")
	protected EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
}
