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

public class ApensarProtocoloBuilder {
	
	@Inject ProtocoloDao protocoloDao;

	@Inject HttpClientGoApi httpGoApi;

	@Inject AuditaProtocoloFactory auditFactory;
	
	@Inject DocflowService docflowService;

	@Inject FuncionarioConverter funcionarioConverter;
	
	@Inject DocflowGenericConverter converterDocflow;

	@Inject HelperMessages messages;
	
	private JuntadaProtocoloDto resultApensacaoDto;
	
	private UserFrontDto usuarioDto;
	
	/**
	 * Efetua de fato a apensação, gravando as alterações nos bancos (corporativo e docflow)
	 * Para cada protocolo sem erros durante a validação, define o status como "em apensação" para controle da transação
	 * até que se finalize. 
	 * 
	 * @param dto - contém os protocolos validados envolvidos na apensação
	 * @param usuario
	 * @return this 
	 * 
	 */
	
	public ApensarProtocoloBuilder juntarProtocoloApenso(JuntadaProtocoloDto dto, UserFrontDto usuario) {
		resultApensacaoDto = dto;
		usuarioDto = usuario;
		
		if (!dto.possuiErrosNaJuntada()) {
			
			protocoloDao.setStatusTransacaoProtocolos(dto.getProtocoloPrincipal().getNumeroProtocolo(), new Long(3));
			protocoloDao.setStatusTransacaoProtocolos(dto.getProtocoloDaJuntada().getNumeroProtocolo(), new Long(3));
			
			juntarProtocoloApensoDocflow().juntarProtocoloApensoCorporativo();
			
			
		}
		return this;
	}
	
	/**
	 * Junta os protocolo no Docflow.
	 * Se os protocolos são digitais e foram validados sem erros, realiza a juntada no Docflow  
	 * e adiciona mensagem de erro no dto caso haja falha no consumo deste serviço na api do Docflow
	 * @return this
	 */
	public ApensarProtocoloBuilder juntarProtocoloApensoDocflow() {
		ResponseJuntadaProtocoloDocflow responseDocflow = new ResponseJuntadaProtocoloDocflow();

		if (resultApensacaoDto.getProtocoloPrincipal().isDigital()) {

			responseDocflow = docflowService.apensarProtocolo(converterDocflow.toDocFlowGenericDto(resultApensacaoDto));
			setMensagemJuntadaDocflow(responseDocflow);
		}

		return this;
	}
	
	/**
	 * Apensa o protocolo no Corporativo
	 * 
	 * Se o protocolo não possui erros, executa as seguintes ações: grava a
	 * referência do protocolo principal no protocolo apensado e normaliza o status da transação, deixando o protocolo
	 * apto a ser trabalhado nas demais ações (status = 0 'NORMAL').
	 * @return this
	 */
	public ApensarProtocoloBuilder juntarProtocoloApensoCorporativo() {
		
		Long principal = resultApensacaoDto.getProtocoloPrincipal().getNumeroProtocolo();
		Long apenso = resultApensacaoDto.getProtocoloDaJuntada().getNumeroProtocolo();
		if (!resultApensacaoDto.possuiErrosNaJuntada()) {

			protocoloDao.juntarProtocolo(principal, apenso, TipoJuntadaProtocoloEnum.APENSACAO);
			
			resultApensacaoDto.setPossuiErrosNaJuntada(false);
			resultApensacaoDto.getMensagensJuntada().add(messages.confirmacaoApensoProtocolo(principal, apenso));
			
			String mensagem = messages.confirmacaoAnexoProtocolo(principal, apenso);
			auditFactory.auditaAcaoProtocolo(resultApensacaoDto, usuarioDto, mensagem, resultApensacaoDto.getModulo(), false);
		}

		protocoloDao.setStatusTransacaoProtocolos(principal, new Long(0));
		protocoloDao.setStatusTransacaoProtocolos(apenso, new Long(0));
		return this;
	}
	
	/**
	 * Adiciona mensagem de erro nas mensagens de validação, se ocorrer falha no consumo do serviço
	 * na api Docflow
	 * @param dto
	 * @param responseDocflow
	 */
	public void setMensagemJuntadaDocflow(ResponseJuntadaProtocoloDocflow responseDocflow) {
		
		if (responseDocflow.hasError()) {
			
			resultApensacaoDto.setPossuiErrosNaJuntada(true);
			resultApensacaoDto.getMensagensJuntada().add(messages.erroJuntadaApensoDocflow(resultApensacaoDto, responseDocflow.getMessage().getValue()));
			
			String error = responseDocflow.getMessage().getValue();
			auditFactory.auditaAcaoProtocolo(resultApensacaoDto, usuarioDto, error, resultApensacaoDto.getModulo(), true);
			
		}
	}
	
	/**
	 * Retorna resultApensacaoDto com o resultado da apensação
	 * @return resultApensacaoDto
	 */
	public JuntadaProtocoloDto buildApenso() {
		return resultApensacaoDto;
	}

}
