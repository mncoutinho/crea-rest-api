package br.org.crea.restapi.siacol.relatorio.xls;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol02Dto;
import br.org.crea.siacol.builder.relxls.SiacolRelXlsBuilder;

public class Relatorio02XlsTest {

   private List<RelSiacol02Dto> listRel;
  
   private SiacolRelXlsBuilder builder;
	
	
	@Test
	public void relatoriosTest() {
		
		try {

			File file = new File("/opt/temp/rel02.xls");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(builder.rel02(listRel));
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


	private List<RelSiacol02Dto> mockRel() {
		List<RelSiacol02Dto> listRel = new ArrayList<>();
		
		for (int i = 0; i < 4; i++) {
			RelSiacol02Dto rel = new RelSiacol02Dto();
			rel.setAssunto("Assunto n: "  + i);
			rel.setQtdConcedidoRegistroProvisorio(i);
			rel.setQtdAprovadoRegistroProvisorio(i);
			rel.setQtdConcedidoAdReferendum(i);
			rel.setQtdAprovadoAdReferendum(i);
			rel.setQtdTotalADeRP(i);
			rel.setQtdReuniaoVirtual(i);
			rel.setQtdReuniaoPresencial(i);
			rel.setQtdTotalReunioes(i);
			
			listRel.add(rel);
		}
		
		
		return listRel;
	}

}
