package br.org.crea.restapi.commons;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.commons.Protocolo;

public class ProtocoloTest {
	
	private String numeroBom;
	
	private String numeroErrado;

	@Before
	public void setup(){
		numeroBom = "1935100205";
		numeroErrado = "2987542113";
	}
	
	public boolean validarNumeroProtocolo(String numero)
	{
		
		if( numero.matches("//([0-9])\\d{9,11}//") )
		{
			if( numero.substring(0, 3).matches("^(19[3456789]\\d|20[01]\\d)[^0-9]") )
			{
				
				return true;
			}
			
		}
		
		return false;
		
	}
	
	
	@Test
	public void deveValidarFormatoNumeroProtocolo(){
		
		validarNumeroProtocolo(numeroBom);
		validarNumeroProtocolo(numeroErrado);
	}
	
	
	@Test
	public void deveValidarSeExisteNoBancoNumeroProtocolo(){

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("dsCreaTest");
		EntityManager em = emf.createEntityManager();
		
		try {
			
			Protocolo protocolo = new Protocolo();
			
			ProtocoloDao dao = new ProtocoloDao();
			dao.setEntityManager(em);
			
			protocolo = dao.getProtocoloBy(new Long(1935100205));
			
			System.out.println(protocolo.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}