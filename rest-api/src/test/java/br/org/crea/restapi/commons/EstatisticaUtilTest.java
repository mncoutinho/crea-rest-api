package br.org.crea.restapi.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.org.crea.commons.util.EstatisticaUtil;

public class EstatisticaUtilTest {
	
	@Test
	public void deveTrazerModaUnica() {
		
		List<Integer> lista = new ArrayList<Integer>(Arrays.asList(1, 3, 4, 3, 4, 3, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4));
		
		System.out.println(EstatisticaUtil.modaUnica(lista));
	}
	
}
