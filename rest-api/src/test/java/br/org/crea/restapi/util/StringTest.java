package br.org.crea.restapi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.format.Formatter;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class StringTest {

	// @Test
	public void formataCpfCnpj() {
		Formatter formatter = new CPFFormatter();
		System.out.println(formatter.format("05152634783"));

	}

//	@Test
	public void retiraacentos() {
		
		String path = "";
		
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		hoje.format(formatador); //08/04/2014
		
		System.out.println(hoje.format(formatador));

	}

	// @Test
	public void teste() {
		String _token = "eyJhbGciOiJSUzI1NiIsImN0eSI6ImFwcGxpY2F0aW9uL2pzb24ifQ.eyJyZWdpc3RybyI6bnVsbCwidGlwb1Blc3NvYSI6IkZVTkNJT05BUklPIiwiY3BmT3VDbnBqIjoiNTE4MTgxMDg3MDQiLCJub21lIjoiS0FUSUEgVkVORVpBIEdPTkNBTFZFUyIsInJhemFvU29jaWFsIjpudWxsLCJ0b2tlbiI6bnVsbCwiYmFzZTY0IjoiIn0.bCSCw5kMKJcobDO_A_77oNLMRkF1ekcxjffV_ggZlsVaCHKFhk5Mla5OgC3AqAEzYpJrkD6X_FdxFKvY6MQMF4HUJNesEMUMUNAeYZULucpdq10Zlmj79t4jjF5lj8R0PPnwVwtQMSHwlObbkhbRd2NiQMid_OO7NBVJy4_E-ebM";

		System.out.println("users:" + _token.substring(_token.length() - 12).replace("-", "").replace("_", ""));

	}

	// @Test
	public void testeCnpj() {

		Date data = new Date();
		String teste = DateUtils.format(data, DateUtils.a);
		// dto.setHoraFormatada(DateUtils.format(d, DateUtils.HH_MM));
		System.out.println(teste);

	}
	
	@Test
	public  void testeStringNullOuVazia() {
		String numero = null;
		Assert.assertTrue(StringUtil.isBlank(numero));
	}

}
