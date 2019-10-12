package br.org.crea.restapi.atendimento;

import org.junit.Test;

import br.org.crea.commons.models.atendimento.dtos.PainelAtendimentoDto;
import br.org.crea.commons.service.HttpFirebaseApi;

public class PainelFirebaseHttpTest {

	HttpFirebaseApi http = new HttpFirebaseApi();
	
	@Test
	public void aplicarModeloArtTest(){ 
		PainelAtendimentoDto atendimento = new PainelAtendimentoDto();
		atendimento.setId(4L);
		atendimento.setNome("TESTE PAINEL 4");
		atendimento.setGuiche("3");
		atendimento.setIdDepartamento(23020401L);
//		http.setFirebaseUrl("https://crea-6a3fd.firebaseio.com/dev-painel-atendimento");
		http.salvarPainelAtendimento(atendimento);
		
	}

	

}
