package br.org.crea.restapi.siacol.relatorio.xls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.Rel14DepartamentosSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol14Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio14XlsTest {

   private List<RelSiacol14Dto> listRel;
  
   private SiacolRelXlsBuilder builder;
	
	
	@Test
	public void relatoriosTest() {
		
		try {
			File file = new File("/opt/temp/rel14.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel14(listRel));
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


	private List<RelSiacol14Dto> mockRel() {
		List<RelSiacol14Dto> listRel = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			RelSiacol14Dto rel = new RelSiacol14Dto();
			rel.setMes("mes " + i);
			
			List<Rel14DepartamentosSiacolDto> depts = new ArrayList<Rel14DepartamentosSiacolDto>();
			Rel14DepartamentosSiacolDto dept = new Rel14DepartamentosSiacolDto();
			dept.setNome("departamento " + i);
			dept.setQtdReuniaoPresencial(i);
			dept.setQtdReuniaoVirtual(i);
			listRel.add(rel);
		}
		
		return listRel;
	}

}
