package br.org.crea.restapi.siacol.relatorio.xls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol11Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio11XlsTest {

   private List<RelSiacol11Dto> listRel;
  
   private SiacolRelXlsBuilder builder;
	
	
	@Test
	public void relatoriosTest() {
		
		try {

			File file = new File("/opt/temp/rel11.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel11(listRel));
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


	private List<RelSiacol11Dto> mockRel() {
		List<RelSiacol11Dto> listRel = new ArrayList<>();
		
		
		for (int i = 0; i < 6; i++) {
			RelSiacol11Dto rel = new RelSiacol11Dto();
			rel.setData(new Date("01/02/2018"));
			rel.setNumeroProtocolo("1233 " + i);
			rel.setTextoAuditoria("..... . " + i);
			listRel.add(rel);
		}
		
		return listRel;
	}

}
