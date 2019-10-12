package br.org.crea.restapi.siacol.relatorio.xls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol06Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio06XlsTest {

   private List<RelSiacol06Dto> listRel;
  
   private SiacolRelXlsBuilder builder;
	
	
	@Test
	public void relatoriosTest() {
		
		try {

			File file = new File("/opt/temp/rel06.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel06(listRel));
			bos.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	@Before
	public  void inicio() {
		builder = new SiacolRelXlsBuilder();
		listRel = mockRel();
	}


	private List<RelSiacol06Dto> mockRel() {
		List<RelSiacol06Dto> listRel = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			RelSiacol06Dto rel = new RelSiacol06Dto();
			rel.setMes("mes > " + i);
			rel.setEntrada(i);
			rel.setSaida(i);
			rel.setPassivo(i);
			rel.setPercentual("% " + i);
			rel.setTotal(i);
			listRel.add(rel);
		}
		
		return listRel;
	}

}
