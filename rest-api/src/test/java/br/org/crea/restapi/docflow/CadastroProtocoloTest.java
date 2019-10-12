package br.org.crea.restapi.docflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.docflow.builder.CadastrarDocumentoProtocoloDocflowBuilder;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.model.response.ResponseCadastroDocumentoDocflow;
import br.org.crea.commons.docflow.util.AuthUtilDocflow;

public class CadastroProtocoloTest {
	
	private static EntityManager em = null;

	private CadastrarDocumentoProtocoloDocflowBuilder cadastroBuilder;
	private ResponseCadastroDocumentoDocflow response;
	private DocflowGenericDto dto;
	

	
	@Before
	public void inicio() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		
		dto = new DocflowGenericDto();

	}
	
	
	@Test
	public void deveCriarUmProtocoloComBaseEmDocumentoExistente() throws Exception {
	
		
		dto = mockDto();
		cadastroBuilder = new CadastrarDocumentoProtocoloDocflowBuilder();
		response = cadastroBuilder.cadastrarMetadadosDocumento(dto).uploadBinarioDocumentoPdf(dto)
				                  .autuarProcessoComBaseEmDocumentoExistente(dto).buildCadastroDocumento();
		
		System.out.println(">>> Resposta: " + response.getMessage().getType());
		System.out.println(">>> Mensagem do Docflow: " + response.getMessage().getValue());
	}
	
	private DocflowGenericDto mockDto() throws Exception {

		DocflowGenericDto dto = new DocflowGenericDto();
		
		InputStream myInputStream = null;
		
		try {

			File initialFile = new File("/home/monique/Área de Trabalho/teste.pdf");
			myInputStream = new FileInputStream(initialFile);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS");
		Date date = new Date();
		String dataArquivo = dateFormat.format(date);

		AuthUtilDocflow auth = new AuthUtilDocflow();
		dto.setToken(auth.auth().getAutenticationData().getAuthToken());
		dto.setAssunto("TESTE - CADASTRO PRT COM BASE EM DOCUMENTO EXISTENTE");
		dto.setUnidadeDestino("230301");
		dto.setDataArquivo(dataArquivo);
		dto.setInputStreamArquivoPdf(myInputStream);
		dto.setInteressado("MONIQUE - TESTE");
		dto.setMatricula("3945");
		dto.setNumeroProtocolo("201170064470");
		dto.setNomeArquivoPdf("teste.pdf");
		dto.setNumeroProcesso("2010121267");
		dto.setObservacao("TESTE - TESTE");
		dto.setTipoDocumento("2");
		dto.setTipoProcesso("7");
		
		return dto;
	}
	
	

}
