package br.org.crea.commons.builder.protocolo;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.funcionario.FuncionarioConverter;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.docflow.converter.DocflowGenericConverter;
import br.org.crea.commons.docflow.model.response.ResponseJuntadaProtocoloDocflow;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.factory.AuditaProtocoloFactory;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.protocolo.enuns.TipoJuntadaProtocoloEnum;
import br.org.crea.commons.service.HttpClientGoApi;

public class AnexarProtocoloBuilder {

	@Inject ProtocoloDao protocoloDao;

	@Inject HttpClientGoApi httpGoApi;

	@Inject AuditaProtocoloFactory auditFactory;
	
	@Inject DocflowService docflowService;

	@Inject FuncionarioConverter funcionarioConverter;
	
	@Inject DocflowGenericConverter converterDocflow;

	@Inject HelperMessages messages;
	
	private JuntadaProtocoloDto resultAnexacaoDto;
	
	private UserFrontDto usuarioDto;
	
	/**
	 * Efetua de fato a anexação, gravando as alterações nos bancos (corporativo e docflow)
	 * Para cada protocolo sem erros, define o status como "em anexação" para controle da transação
	 * até que se finalize.
	 * 
	 * @param dto - contém os protocolos validados envolvidos na anexação
	 * @return this 
	 * 
	 */
	
	public AnexarProtocoloBuilder juntarProtocoloAnexo(JuntadaProtocoloDto dto, UserFrontDto usuario) {
		resultAnexacaoDto = dto;
		usuarioDto = usuario;
		
		if (!dto.possuiErrosNaJuntada()) {
			
			protocoloDao.setStatusTransacaoProtocolos(dto.getProtocoloPrincipal().getNumeroProtocolo(), new Long(2));
			protocoloDao.setStatusTransacaoProtocolos(dto.getProtocoloDaJuntada().getNumeroProtocolo(), new Long(2));
			
			juntarProtocoloAnexoDocflow().juntarProtocoloAnexoCorporativo();
			
		}
		return this;
	}
	
	/**
	 * Junta os protocolo no Docflow.
	 * Se os protocolos são digitais e foram validados sem erros, realiza a juntada no Docflow  
	 * e adiciona mensagem de erro no dto caso haja falha no consumo deste serviço na api do Docflow
	 * @return this
	 */
	public AnexarProtocoloBuilder juntarProtocoloAnexoDocflow() {
		ResponseJuntadaProtocoloDocflow responseDocflow = new ResponseJuntadaProtocoloDocflow();

		if (resultAnexacaoDto.getProtocoloPrincipal().isDigital()) {

			responseDocflow = docflowService.anexarProtocolo(converterDocflow.toDocFlowGenericDto(resultAnexacaoDto));
			setMensagemJuntadaDocflow(responseDocflow);
		}

		return this;
	}

	/**
	 * Anexa o protocolo no Corporativo
	 * 
	 * Se o protocolo não possui erros, executa as seguintes ações: grava a
	 * referência do protocolo principal no protocolo anexado e normaliza o status da transação, deixando o protocolo
	 * apto a ser trabalhado nas demais ações (status = 0 'NORMAL').
	 * @return this
	 */
	public AnexarProtocoloBuilder juntarProtocoloAnexoCorporativo() {
		
		Long principal = resultAnexacaoDto.getProtocoloPrincipal().getNumeroProtocolo();
		Long anexo = resultAnexacaoDto.getProtocoloDaJuntada().getNumeroProtocolo();
		if (!resultAnexacaoDto.possuiErrosNaJuntada()) {

			protocoloDao.juntarProtocolo(principal, anexo, TipoJuntadaProtocoloEnum.ANEXACAO);
			
			resultAnexacaoDto.setPossuiErrosNaJuntada(false);
			resultAnexacaoDto.getMensagensJuntada().add(messages.confirmacaoAnexoProtocolo(principal, anexo));
			
			String mensagem = messages.confirmacaoAnexoProtocolo(principal, anexo);
			auditFactory.auditaAcaoProtocolo(resultAnexacaoDto, usuarioDto, mensagem, resultAnexacaoDto.getModulo(), false);
		}

		protocoloDao.setStatusTransacaoProtocolos(principal, new Long(0));
		protocoloDao.setStatusTransacaoProtocolos(anexo, new Long(0));
		return this;
	}
	
	/**
	 * Adiciona mensagem de erro nas mensagens de validação, se ocorrer falha no consumo do serviço
	 * na api Docflow
	 * @param responseDocflow
	 */
	public void setMensagemJuntadaDocflow(ResponseJuntadaProtocoloDocflow responseDocflow) {
		
		if (responseDocflow.hasError()) {
			
			resultAnexacaoDto.setPossuiErrosNaJuntada(true);
			resultAnexacaoDto.getMensagensJuntada().add(messages.erroJuntadaAnexoDocflow(resultAnexacaoDto, responseDocflow.getMessage().getValue()));
			
			String error = responseDocflow.getMessage().getValue();
			auditFactory.auditaAcaoProtocolo(resultAnexacaoDto, usuarioDto, error, resultAnexacaoDto.getModulo(), true);
			
		}
	}
	
	/**
	 * Retorna resultAnexacaoDto com o resultado da anexação
	 * @return resultAnexacaoDto
	 */
	public JuntadaProtocoloDto buildAnexo() {
		return resultAnexacaoDto;
	}

}
