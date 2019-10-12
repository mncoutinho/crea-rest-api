package br.org.crea.restapi.util;

import org.junit.Test;

import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;

public class TestDoc {
	
	@Test
	public void teste() {
		DocumentoDto d = new DocumentoDto();
		
		d.setAssinatura("aaa");
		System.out.println(d.getAssinatura());
	}

}
