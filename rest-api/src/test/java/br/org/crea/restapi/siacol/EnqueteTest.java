package br.org.crea.restapi.siacol;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.EnqueteDto;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;


public class EnqueteTest {
private static EntityManager em = null;
	
	@BeforeClass
	public static void inicio() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		Assert.assertTrue("entity manager iniciado", em != null);
	}
	
	@AfterClass
	public static void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	
	@Test
	public void ApuraEnquete() {
		
		RlDocumentoProtocoloSiacol item = getEnquete(new Long(3137));
		
		ItemPautaDto itemDto = new ItemPautaDto();
		
		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			
			itemDto.setEnqueteDto(mapper.readValue(item.getEnquete(), EnqueteDto.class));
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	private RlDocumentoProtocoloSiacol getEnquete(Long idItem) {
		
		RlDocumentoProtocoloSiacol item = new RlDocumentoProtocoloSiacol();
		
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT I FROM RlDocumentoProtocoloSiacol I   ");
		sql.append("     WHERE I.ID = :idItem                     ");

		
		try {
			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idItem", idItem);
			
			item = query.getSingleResult();
			
		} catch (NoResultException e) {
			System.out.println("Sem resultados");
		} catch (Throwable e) {
			System.err.println("Erro:" + e.getMessage());
		}
		
		return item;
	}


}
