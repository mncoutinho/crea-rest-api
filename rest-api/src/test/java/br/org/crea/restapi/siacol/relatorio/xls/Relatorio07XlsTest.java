package br.org.crea.restapi.siacol.relatorio.xls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelDepartamentosSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol03Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol07Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio07XlsTest {

   private List<RelSiacol07Dto> listRel;
  
   private SiacolRelXlsBuilder builder;
	
	
	@Test
	public void relatoriosTest() {
		
		try {

			File file = new File("/opt/temp/rel07.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel07(listRel));
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


	private List<RelSiacol07Dto> mockRel() {
		List<RelSiacol07Dto> listRel = new ArrayList<>();
		
	List<RelDepartamentosSiacolDto> departamentos = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			RelSiacol03Dto rel = new RelSiacol03Dto();
			rel.setEvento("evento > " + i );
			rel.setTotal(i);
			for (int j = 0; j < 3; j++) { 
				RelDepartamentosSiacolDto departamento = new RelDepartamentosSiacolDto();
				departamento.setNome("dep " + j);
				departamento.setQtd(j);
				departamentos.add(departamento);
			}
			rel.setDepartamentos(departamentos);
//			listRel.add(rel);
			departamentos = new ArrayList<>();
		}
		
		
		
		
		return listRel;
	}

}
