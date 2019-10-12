package br.org.crea.restapi.util;

import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;

public class Teste {
	
	@Test
	public void teste() {
		
		int numero = 15;
		int result = 0;
		String binario = Integer.toBinaryString(numero);
		System.out.println(binario);
		
		if(binario.contains("0")) {
			System.out.println("tem 0");
		}
		
		if(binario.contains("1")) {
			System.out.println("tem 1");
		}
		
	}

}
