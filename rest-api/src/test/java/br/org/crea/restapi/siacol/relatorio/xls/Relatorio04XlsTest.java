package br.org.crea.restapi.siacol.relatorio.xls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelDepartamentosSiacol04Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol04Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio04XlsTest {

   private List<RelSiacol04Dto> listRel;
  
   private SiacolRelXlsBuilder builder;
	
	
	@Test
	public void relatoriosTest() {
		
		try {

			File file = new File("/opt/temp/rel04.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel04(listRel));
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


	private List<RelSiacol04Dto> mockRel() {
		List<RelSiacol04Dto> listRel = new ArrayList<>();
		List<RelDepartamentosSiacol04Dto> departamentos = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			RelSiacol04Dto rel = new RelSiacol04Dto();
			rel.setAssunto("assunto > " + i );
			rel.setTotal(i);
			for (int j = 0; j < 3; j++) { 
				RelDepartamentosSiacol04Dto departamento = new RelDepartamentosSiacol04Dto();
				departamento.setNome("dep " + j);
				departamento.setQtd(String.valueOf(j));
				departamentos.add(departamento);
			}
			rel.setDepartamentos(departamentos);
			listRel.add(rel);
			departamentos = new ArrayList<>();
		}
		
		return listRel;
	}

}
