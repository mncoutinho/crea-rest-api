package br.org.crea.restapi.commons;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TesteArrayList {
	
	@Test
	public void testeListaeArray () {
		String[] vetor = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};

		List<String> listaApartirDeArray = Arrays.asList(vetor);
//		List<String> lista = new ArrayList<String>(Arrays.asList(vetor));
		
		System.out.println("lista");
		listaApartirDeArray.forEach(item -> {
			System.out.println(item);
		});
		listaApartirDeArray.add("J");
		
		listaApartirDeArray.forEach(item -> {
			System.out.println(item);
		});
		
		System.out.println("lista a partir de array");
		
		listaApartirDeArray.forEach(item -> {
			System.out.println(item);
		});
		listaApartirDeArray.add("J");
		
		listaApartirDeArray.forEach(item -> {
			System.out.println(item);
		});
		
	}
	
}
