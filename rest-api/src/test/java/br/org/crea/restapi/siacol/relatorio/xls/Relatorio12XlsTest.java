package br.org.crea.restapi.siacol.relatorio.xls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol12Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio12XlsTest {

   private List<RelSiacol12Dto>listRel;
  
   private SiacolRelXlsBuilder builder;
	
	
	@Test
	public void relatoriosTest() {
		
		try {

			File file = new File("/opt/temp/rel12.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel12(listRel));
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


	private List<RelSiacol12Dto> mockRel() {
			
		List<RelSiacol12Dto> listRelatorio = new ArrayList<RelSiacol12Dto>();
		
		RelSiacol12Dto rel = new RelSiacol12Dto();
		rel.setDepartamento("Dep " + 3);
		rel.setQtdDecisoesFavoraveis(4);
		rel.setQtdDecisoesDesfavoraveis(5);
		rel.setQtdDecisoesHomologacao(6);
		rel.setQtdDecisoesAssunto(7);
		
		listRelatorio.add(rel);
		
		return listRelatorio;
	}

}
