package br.org.crea.restapi.commons;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.util.ExcelPoiUtil;

public class ExcelPoiUtilTest {
	
	ExcelPoiUtil excelPoiUtil = null;
	
	@Before
	public  void inicio() {
		excelPoiUtil = new ExcelPoiUtil();
	}
	
	@After
	public void fim() {
		excelPoiUtil = null;
		
	}
	
	@Test
	public void deveGerarPlanilhaNoFileSystem() {
		
		try {
			excelPoiUtil.setCell("cpf");
			excelPoiUtil.setCell("nome");
			excelPoiUtil.setCell("dataFormaturaPlanilha");
			excelPoiUtil.setCell("curso");
			excelPoiUtil.newRow();
			excelPoiUtil.setCell("");
			excelPoiUtil.setCell("");
			excelPoiUtil.setCell("");
			excelPoiUtil.setCell("TI");
			excelPoiUtil.buildToFile("teste.xls");
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
			
	}


}
