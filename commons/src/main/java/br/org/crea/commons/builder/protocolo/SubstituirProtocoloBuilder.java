package br.org.crea.commons.builder.protocolo;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.funcionario.FuncionarioConverter;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.docflow.converter.DocflowGenericConverter;
import br.org.crea.commons.docflow.model.response.ResponseSubstituicaoProtocoloDocflow;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.factory.AuditaProtocoloFactory;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.protocolo.dtos.SubstituicaoProtocoloDto;
import br.org.crea.commons.service.HttpClientGoApi;

public class SubstituirProtocoloBuilder {
	
	@Inject ProtocoloDao protocoloDao;

	@Inject HttpClientGoApi httpGoApi;

	@Inject AuditaProtocoloFactory auditFactory;
	
	@Inject DocflowService docflowService;

	@Inject FuncionarioConverter funcionarioConverter;
	
	@Inject DocflowGenericConverter converterDocflow;

	@Inject HelperMessages messages;
	
	private SubstituicaoProtocoloDto resultSubstituicao;
	
	private UserFrontDto usuarioDto;

	/**
	 * Efetua de fato a substituição, gravando as alterações nos bancos (corporativo e docflow)
	 * Para cada protocolo sem erros, define o status como "em substituição" para controle da transação
	 * até que se finalize.
	 * 
	 * @param dto - contém os protocolos validados envolvidos na substituição
	 * @return this 
	 * 
	 */
	
	public SubstituirProtocoloBuilder substituirProtocolo(SubstituicaoProtocoloDto dto, UserFrontDto usuario) {
		resultSubstituicao = dto;
		usuarioDto = usuario;
		
		if (!dto.possuiErrosSubstituicao()) {
			
			protocoloDao.setStatusTransacaoProtocolos(dto.getProtocoloSubstituido().getNumeroProtocolo(), new Long(4));
			protocoloDao.setStatusTransacaoProtocolos(dto.getProtocoloSubstituto().getNumeroProtocolo(), new Long(4));
			
			substituirProtocoloDocflow().substituirProtocoloCorporativo();
			
		}
		return this;
	}
	
	/**
	 * Substituir os protocolos no Docflow.
	 * Se os protocolos são digitais e foram validados sem erros, realiza a substituição no Docflow  
	 * e adiciona mensagem de erro no dto caso haja falha no consumo deste serviço na api do Docflow
	 * @return this
	 */
	public SubstituirProtocoloBuilder substituirProtocoloDocflow() {
		ResponseSubstituicaoProtocoloDocflow responseDocflow = new ResponseSubstituicaoProtocoloDocflow();

		if (resultSubstituicao.getProtocoloSubstituto().isDigital()) {

			responseDocflow = docflowService.substituirProtocolo(converterDocflow.toDocFlowGenericDto(resultSubstituicao));
			setRetornoSubstituicaoDocflow(responseDocflow);
		}

		return this;
	}
	
	/**
	 * Adiciona mensagem de erro nas mensagens de validação, se ocorrer falha no consumo do serviço
	 * na api Docflow
	 * @param responseDocflow
	 */
	public void setRetornoSubstituicaoDocflow(ResponseSubstituicaoProtocoloDocflow responseDocflow) {
	
		if (responseDocflow.hasError()) {
			
			resultSubstituicao.setPossuiErrosSubstituicao(true);
			resultSubstituicao.getMensagensSubstituicao().add(messages.erroSubstituicaoProtocoloDocflow(resultSubstituicao, responseDocflow.getMessage().getValue()));
			
			String error = responseDocflow.getMessage().getValue();
			auditFactory.auditaAcaoProtocolo(resultSubstituicao, usuarioDto, error, resultSubstituicao.getModuloSistema(), true);
			
		}
	}
	
	/**
	 * Se o protocolo não possui erros e sendo eletrônico, foi substituido com sucesso no Docflow,
	 * realiza as seguintes ações:
	 * - Finaliza o protocolo substituido
	 * - Grava a referência do protocolo substituto no protocolo substituido
	 * - Audita a ação de substituição entre os protocolos
	 * - Normaliza o status dos protocolos da substituição
	 * @return this
	 * */
	public SubstituirProtocoloBuilder substituirProtocoloCorporativo() {
		
		Long protocoloSubstituido = resultSubstituicao.getProtocoloSubstituido().getNumeroProtocolo();
		Long protocoloSubstituto = resultSubstituicao.getProtocoloSubstituto().getNumeroProtocolo();
		
		if(!resultSubstituicao.possuiErrosSubstituicao()) {
			
			String mensagem = messages.confirmacaoSubstituicaoProtocolo(resultSubstituicao);
			
			protocoloDao.substituirProtocolo(resultSubstituicao);
			resultSubstituicao.setPossuiErrosSubstituicao(false);
			resultSubstituicao.getMensagensSubstituicao().add(mensagem);
			auditFactory.auditaAcaoProtocolo(resultSubstituicao, usuarioDto, mensagem, resultSubstituicao.getModuloSistema(), false);

		}
		
		protocoloDao.setStatusTransacaoProtocolos(protocoloSubstituido, new Long(0));
		protocoloDao.setStatusTransacaoProtocolos(protocoloSubstituto, new Long(0));
		return this;
	}

	/**
	 * Retorna resultSubstituicao com o resultado da substituição
	 * @return resultSubstituicao
	 */
	public SubstituicaoProtocoloDto build() {
		return resultSubstituicao;
	}
}
