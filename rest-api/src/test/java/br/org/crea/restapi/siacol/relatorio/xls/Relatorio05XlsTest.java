package br.org.crea.restapi.siacol.relatorio.xls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol05Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.StatusQtdDto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio05XlsTest {

   private List<RelSiacol05Dto> listRel;
  
   private SiacolRelXlsBuilder builder;
	
	
	@Test
	public void relatoriosTest() {
		
		try {

			File file = new File("/opt/temp/rel05.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel05(listRel));
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


	private List<RelSiacol05Dto> mockRel() {
		List<RelSiacol05Dto> listRel = new ArrayList<>();
		List<StatusQtdDto> listStatus = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			RelSiacol05Dto rel = new RelSiacol05Dto();
//			rel.setDepartamento("departamento > " + i );
			for (int j = 0; j < 3; j++) { 
				StatusQtdDto status = new StatusQtdDto();
				status.setStatus("status >" + j);
				status.setTotal(j);
				listStatus.add(status);
			}
//			rel.setStatus(listStatus);
			listRel.add(rel);
			listStatus = new ArrayList<>();
		}
		
		return listRel;
	}

}
