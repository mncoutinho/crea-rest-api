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

public class DesvincularJuntadaProtocoloBuilder {

	@Inject ProtocoloDao protocoloDao;

	@Inject HttpClientGoApi httpGoApi;

	@Inject AuditaProtocoloFactory auditFactory;
	
	@Inject DocflowService docflowService;

	@Inject FuncionarioConverter funcionarioConverter;
	
	@Inject DocflowGenericConverter converterDocflow;

	@Inject HelperMessages messages;
	
	private JuntadaProtocoloDto resultDesvinculacaoDto;
	
	private UserFrontDto usuarioDto;
	
	/**
	 * Efetua de fato a desanexação ou desapensação, gravando as alterações nos bancos (corporativo e docflow)
	 * Para cada protocolo sem erros, define o status do protocolo para controle da transação
	 * até que se finalize.
	 * 
	 * @param dto - contém os protocolos validados envolvidos na desvinculação
	 * @return this 
	 * 
	 */
	
	public DesvincularJuntadaProtocoloBuilder desvincularProtocolo(JuntadaProtocoloDto dto, UserFrontDto usuario) {
		resultDesvinculacaoDto = dto;
		usuarioDto = usuario;
		
		if (!dto.possuiErrosNaJuntada()) {
			
			protocoloDao.setStatusTransacaoProtocolos(dto.getProtocoloPrincipal().getNumeroProtocolo(), new Long(8));
			protocoloDao.setStatusTransacaoProtocolos(dto.getProtocoloDaJuntada().getNumeroProtocolo(), new Long(8));
			
			desvincularProtocoloDocflow().desvincularProtocoloCorporativo();
		}
		return this;
	}
	
	/**
	 * Desvincula os protocolos no Docflow.
	 * Se os protocolos são digitais e foram validados sem erros, realiza a desvinculação no Docflow  
	 * e adiciona mensagem de erro no dto caso haja falha no consumo deste serviço na api do Docflow
	 * @return this
	 */
	public DesvincularJuntadaProtocoloBuilder desvincularProtocoloDocflow() {
		ResponseJuntadaProtocoloDocflow responseDocflow = new ResponseJuntadaProtocoloDocflow();

		if (resultDesvinculacaoDto.getProtocoloPrincipal().isDigital()) {

			if( resultDesvinculacaoDto.getTipoJuntadaProtocolo().equals(TipoJuntadaProtocoloEnum.DESAPENSACAO) ) {
				
				responseDocflow = docflowService.desapensarProtocolo(converterDocflow.toDocFlowGenericDto(resultDesvinculacaoDto));
				
			} else if ( resultDesvinculacaoDto.getTipoJuntadaProtocolo().equals(TipoJuntadaProtocoloEnum.DESANEXACAO) ){
				
				responseDocflow = docflowService.desanexarProtocolo(converterDocflow.toDocFlowGenericDto(resultDesvinculacaoDto));
			}
			setMensagemDesvinculoDocflow(responseDocflow);
		}

		return this;
	}
	
	/**
	 * Desvincula o protocolo no Corporativo
	 * 
	 * Se o protocolo não possui erros, executa as seguintes ações: retira a
	 * referência do protocolo principal do protocolo vinculado e normaliza o status da transação, deixando o protocolo
	 * apto a ser trabalhado nas demais ações. (status = 0 'NORMAL')
	 * @return this
	 */
	public DesvincularJuntadaProtocoloBuilder desvincularProtocoloCorporativo() {
		
		Long principal = resultDesvinculacaoDto.getProtocoloPrincipal().getNumeroProtocolo();
		Long protocoloVinculado = resultDesvinculacaoDto.getProtocoloDaJuntada().getNumeroProtocolo();
		
		if (!resultDesvinculacaoDto.possuiErrosNaJuntada()) {
			
			if(resultDesvinculacaoDto.getTipoJuntadaProtocolo().equals(TipoJuntadaProtocoloEnum.DESANEXACAO)) {
				
				protocoloDao.desanexarProtocolo(protocoloVinculado);
				resultDesvinculacaoDto.getMensagensJuntada().add(messages.confirmacaoDesanexoProtocolo(principal, protocoloVinculado));
				
			} else if(resultDesvinculacaoDto.getTipoJuntadaProtocolo().equals(TipoJuntadaProtocoloEnum.DESAPENSACAO)) {
				
				protocoloDao.desapensarProtocolo(protocoloVinculado);
				resultDesvinculacaoDto.getMensagensJuntada().add(messages.confirmacaoDesapensoProtocolo(principal, protocoloVinculado));
			}

			resultDesvinculacaoDto.setPossuiErrosNaJuntada(false);
			String mensagem = messages.confirmacaoDesanexoProtocolo(principal, protocoloVinculado);
			auditFactory.auditaAcaoProtocolo(resultDesvinculacaoDto, usuarioDto, mensagem, resultDesvinculacaoDto.getModulo(), false);
		}

		protocoloDao.setStatusTransacaoProtocolos(principal, new Long(0));
		protocoloDao.setStatusTransacaoProtocolos(protocoloVinculado, new Long(0));
		return this;
	}
	
	/**
	 * Adiciona mensagem de erro nas mensagens de validação, se ocorrer falha no consumo do serviço
	 * na api Docflow
	 * @param dto
	 * @param responseDocflow
	 */
	public void setMensagemDesvinculoDocflow(ResponseJuntadaProtocoloDocflow responseDocflow) {
		
		if (responseDocflow.hasError()) {
			
			resultDesvinculacaoDto.setPossuiErrosNaJuntada(true);
			resultDesvinculacaoDto.getMensagensJuntada().add(messages.erroJuntadaDesanexoDocflow(resultDesvinculacaoDto, responseDocflow.getMessage().getValue()));
			
			String error = responseDocflow.getMessage().getValue();
			auditFactory.auditaAcaoProtocolo(resultDesvinculacaoDto, usuarioDto, error, resultDesvinculacaoDto.getModulo(), true);
			
		}
	}
	
	/**
	 * Retorna resultDesvinculacaoDto com o resultado da desvinculação
	 * @return resultDesvinculacaoDto
	 */
	public JuntadaProtocoloDto buildDesvinculo() {
		return resultDesvinculacaoDto;
	}
	
}
