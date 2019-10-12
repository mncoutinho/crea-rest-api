package br.org.crea.restapi.util;

import java.math.BigDecimal;

import org.junit.Test;

public class BigDecimalTest {
	
	@Test
	public void bigDecimalTest() {
		BigDecimal b = new BigDecimal("1");
		System.out.println(b);
		System.out.println(b.compareTo(new BigDecimal("1")) == 0);
		
	}

}
