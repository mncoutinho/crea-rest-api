package br.org.crea.restapi.redis;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.service.HttpClientGoApi;

public class LogTest {
	
	
	private HttpClientGoApi api;
	
	
	@Before
	public void setup(){
		api = new HttpClientGoApi();
	}
	
	@Test
	public void geraLog(){
		
		for (int i = 0; i < 10; i++) {
			api.geraLog("Método teste", "{ name:John, age:30, cit:New York}", new IllegalArgumentException("parametro ilegall"));
		}
		
		
		
	}

}
