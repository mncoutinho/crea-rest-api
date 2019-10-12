package br.org.crea.commons.docflow.builder;

import javax.inject.Inject;

import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.docflow.client.ClientCadastroDocumentoProtocolo;
import br.org.crea.commons.docflow.client.ClientExclusaoConteudoDocflow;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.model.response.ResponseAutuacaoProcessoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseCadastroDocumentoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseCriarProtocoloAssociarDocumentoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseUploadDocumentoDocflow;
import br.org.crea.commons.docflow.util.AuthUtilDocflow;
import br.org.crea.commons.models.commons.Protocolo;

public class CadastrarDocumentoProtocoloDocflowBuilder {
	
	@Inject ClientCadastroDocumentoProtocolo client;
	
	@Inject ClientExclusaoConteudoDocflow clientExclusaoConteudo;
	
	@Inject AuthUtilDocflow authDocflow;
	
	@Inject ProtocoloDao protocoloDao;
	
	private boolean error;
	private ResponseCadastroDocumentoDocflow responseCadastroDocumento;
	private ResponseUploadDocumentoDocflow   responseUploadDocumento;
	private ResponseAutuacaoProcessoDocflow  responseAutuacaoProcesso;
	private ResponseCriarProtocoloAssociarDocumentoDocflow responseCriarProtocoloAssociarDocumento;
	private DocflowGenericDto dto;
	
	
	/**
	 * @author Monique Santos
	 * Método responsável por cadastrar metadados de um documento no Docflow, ou seja, criar a capa do documento neste sistema.
	 * O Docflow permite a criação de um documento independente da existência de um binário (PDF). 
	 * 
	 * No término desta rotina recuperamos o id do documento que foi criado para continuar manipulando o registro. 
	 * */
	public CadastrarDocumentoProtocoloDocflowBuilder cadastrarMetadadosDocumento(DocflowGenericDto metadadosDocumentoDto) {
		
		error = false;
		
		dto = new DocflowGenericDto();
		dto = metadadosDocumentoDto;
		dto.setToken(authDocflow.auth().getAutenticationData().getAuthToken());

		responseCadastroDocumento = new ResponseCadastroDocumentoDocflow();
		responseCadastroDocumento = client.cadastrarMetadadosDocumento(dto);

		if (responseCadastroDocumento.hasError()) {
			
			error = true;
			
		}else{
			
			dto.setIdDocumento(responseCadastroDocumento.getData().getDoc().getId());
			
		}
		return this;
	}

	/**
	 * @author Monique Santos
	 * Método responsável associar um binário (PDF) a um documento existente. O id documento que será consumido aqui é
	 * fornecido pelo método cadastrarMetadadosDocumento. 
	 * */
	public CadastrarDocumentoProtocoloDocflowBuilder uploadBinarioDocumentoPdf(DocflowGenericDto documentoDocflowDto) {
		
		dto = new DocflowGenericDto();
		dto = documentoDocflowDto;
		
		if (!error) {

			responseUploadDocumento = client.uploadBinarioDocumentoPdf(documentoDocflowDto);
			
			if (responseUploadDocumento.hasError()) {
				
				error = true;
				responseCadastroDocumento.setMessage(responseUploadDocumento.getMessage());
			} 
		}
		return this;
	}
	
	/**
	 * @author Monique Santos
	 * Método responsável por autuar um processo (cadastrar um protocolo no Docflow). Esse cadastro 
	 * depende obrigatoriamente de um documento que tenha sido cadastrado anteriormente. 
	 * 
	 * Na ocorrência de erro e na impossibilidade de autuar o processo, o documento que foi criado com o mesmo número
	 * do protocolo do Corporativo será excluído permitindo nova tentativa do usuário em autuar o processo.
	 * */
	public CadastrarDocumentoProtocoloDocflowBuilder autuarProcessoComBaseEmDocumentoExistente(DocflowGenericDto autuacaoProcessoDto) {
		
		dto = new DocflowGenericDto();
		dto = autuacaoProcessoDto;
		
		if (!error) {

			responseAutuacaoProcesso = client.autuarProcessoComBaseEmDocumentoExistente(autuacaoProcessoDto);
			
			if (responseAutuacaoProcesso.hasError()) {
				
				error = true;
				responseCadastroDocumento.setMessage(responseAutuacaoProcesso.getMessage());
				
				dto.setUniqueIdConteudo(dto.getIdDocumento());
				dto.setCodigoDepartamento(dto.getUnidadeDestino());
				clientExclusaoConteudo.excluirDocumento(dto);
			}
		}
		return this;
	}
	
	/**
	 * @author Monique Santos
	 * Método responsável por associar um documento a um protocolo que já foi criado anteriormente
	 * no Docflow.
	 * */
	public CadastrarDocumentoProtocoloDocflowBuilder associarDocumentoAProcessoExistente(DocflowGenericDto documentoDocflowDto) {

		dto = new DocflowGenericDto();
		dto = documentoDocflowDto;
		
		if (!error) {

			responseUploadDocumento = client.associarDocumentoAProcessoExistente(documentoDocflowDto);
			
			if (responseUploadDocumento.hasError()) {
				
				error = true;
				responseCadastroDocumento.setMessage(responseUploadDocumento.getMessage());
			}
		}
		return this;
	}
	
	/**
	 * @author Fernando Nicolau (Calma Informática)
	 * */
	public CadastrarDocumentoProtocoloDocflowBuilder criarProtocoloAssociarDocumento(DocflowGenericDto documentoDocflowDto) {
		
		dto = new DocflowGenericDto();
		dto = documentoDocflowDto;
		
		if (!error) {

			Protocolo protocolo = protocoloDao.getProtocoloBy(new Long(documentoDocflowDto.getProtocoloDoDocumento()));
			if (protocolo != null) {
				String tipoProcesso = Character.toString(String.valueOf(documentoDocflowDto.getProtocoloDoDocumento()).charAt(4));
				documentoDocflowDto.setTipoProcesso(tipoProcesso);
				documentoDocflowDto.setNumeroProcesso(protocolo.getNumeroProcesso().toString());
				responseCriarProtocoloAssociarDocumento = client.criarProtocoloAssociarDocumento(documentoDocflowDto);
				
				if (responseCriarProtocoloAssociarDocumento.hasError()) {
					
					error = true;
					responseCadastroDocumento.setMessage(responseCriarProtocoloAssociarDocumento.getMessage());
				}	
			}
			
		}
		return this;
	}
	
	/**
	 * @author Monique Santos
	 * Retorna a resposta sobre cadastro de documento e / ou processo no Docflow
	 * */
	public ResponseCadastroDocumentoDocflow buildCadastroDocumento() {
		return responseCadastroDocumento;
	}



}
