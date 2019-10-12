package br.org.crea.restapi.siacol.relatorio.xls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol13Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio13XlsTest {

   private List<RelSiacol13Dto> listRel;
  
   private SiacolRelXlsBuilder builder;
	
	
	@Test
	public void relatoriosTest() {
		
		try {

			File file = new File("/opt/temp/rel13.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel13(listRel));
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


	private List<RelSiacol13Dto> mockRel() {
		List<RelSiacol13Dto> listRel = new ArrayList<>();
		
		
		for (int i = 0; i < 6; i++) {
			RelSiacol13Dto rel = new RelSiacol13Dto();
			rel.setNumeroProtocolo(String.valueOf(i));
			rel.setCodigoAssuntoCorporativo(String.valueOf(i));
			rel.setDescricaoAssuntoCorporativo(String.valueOf(i));
			rel.setCodigoAssuntoSiacol(String.valueOf(i));
			rel.setDescricaoAssuntoSiacol(String.valueOf(i));
			listRel.add(rel);
		}
		
		return listRel;
	}

}
