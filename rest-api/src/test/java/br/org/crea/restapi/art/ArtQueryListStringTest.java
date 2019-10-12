package br.org.crea.restapi.art;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.art.Art;

public class ArtQueryListStringTest {
	
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
	}
	
	@After
	public void fim() {
		em.close();
	}

	@Test
	public void teste () {
		List<String> arts = new ArrayList<String>();
		arts.add("1002709");
		arts.add("1002712");
		arts.add("1002713");
		arts.add("1002714");
		
		getArt(arts);
	}
	
	
	public void getArt(List<String> arts){
		List<Art> resultado = new ArrayList<Art>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT A FROM Art A ");
		sql.append("   WHERE A.numero IN (:arts) ");
		
		try{
			TypedQuery<Art> query = em.createQuery(sql.toString(), Art.class);
			query.setParameter("arts", arts);
			
			resultado = query.getResultList();
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}	
		resultado.forEach(art -> {
			System.out.println(art.getNumero() + " | " + art.getNaturezaArt().getDescricao());
		});
		
		
	}

}
