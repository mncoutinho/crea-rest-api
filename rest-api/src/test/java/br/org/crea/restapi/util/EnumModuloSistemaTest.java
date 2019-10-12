package br.org.crea.restapi.util;



import org.junit.Assert;
import org.junit.Test;

import br.org.crea.commons.models.commons.enuns.ModuloSistema;

public class EnumModuloSistemaTest {
	
	
	
	@Test
	public void setup(){
		
		
		Assert.assertEquals(new Long(5), ModuloSistema.getIdBy(ModuloSistema.ATENDIMENTO));
		
	}

}
