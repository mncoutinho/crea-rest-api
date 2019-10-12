package br.org.crea.restapi.docflow;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.docflow.client.ClientTramiteProtocoloDocflow;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.model.response.ResponseTramiteProtocoloDocflow;
import br.org.crea.commons.docflow.util.AuthUtilDocflow;

public class TramitacaoProtocoloTest {
	
	

	private ClientTramiteProtocoloDocflow client;
	private ResponseTramiteProtocoloDocflow response;
	private DocflowGenericDto dto;
	

	@Before
	public void inicio() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
//		em = factory.createEntityManager();
		
		dto = new DocflowGenericDto();
		response = new ResponseTramiteProtocoloDocflow();
		client = new ClientTramiteProtocoloDocflow();
	}
	
	@Test
	public void deveEnviarProtocolo() throws Exception {
		
		dto = mockTramiteEnvio();
		response = client.enviarProtocolo(dto);
		
		System.out.println(">>> Resposta: " + response.getMessage().getType());
		System.out.println(">>> Mensagem do Docflow: " + response.getMessage().getValue());
	}
	
	private DocflowGenericDto mockTramiteEnvio() {

		DocflowGenericDto dto = new DocflowGenericDto();

		AuthUtilDocflow auth = new AuthUtilDocflow();
		dto.setToken(auth.auth().getAutenticationData().getAuthToken());
		dto.setMatricula("3945");
		dto.setNumeroProtocolo("201770000697");
		dto.setUnidadeDestino("23020201");
		dto.setUnidadeOrigem("230301");
		dto.setCodigoSituacao("0");
		dto.setCodigoClassificacaoTramite("0");
		
		return dto;
	}
}
