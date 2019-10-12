package br.org.crea.restapi.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProducesEntityManager {


	private EntityManagerFactory emf;

	@Produces
	public EntityManager criaEntity() {
		emf = Persistence.createEntityManagerFactory("dsCreaTest");
		return emf.createEntityManager();
	}



}
