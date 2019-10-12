package br.org.crea.commons.builder.documento;

import java.util.ArrayList;

import javax.inject.Inject;

import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.service.redis.RedisService;
import br.org.crea.commons.validsigner.dto.ValidSignDto;

public class ValidaAssinaturaDocumentoBuilder {
	
	@Inject HelperMessages messages;
	
	@Inject RedisService redisService;

	private ValidSignDto signDto;
	
	/**
	 * Valida os dados de entrada necessários para processar assinatura digital em um documento
	 * pdf com a Valid Certificadora
	 * @param dto - contém as informações necessárias para validar a pré assinatura
	 * @return this
	 * */
	public ValidaAssinaturaDocumentoBuilder validarPreAssinatura(ValidSignDto dto) {
		signDto = dto;
		signDto.setMensagemValidacao(new ArrayList<String>());
		
		if( parametrosPreSignForamInformados() ) {

			verificaConexaoRedis().verificaDocumentoASerAssinado();
		}
		
		signDto.setAssinaturaValida(signDto.getMensagemValidacao().isEmpty());
		return this;
	}
	
	/**
	 * Valida os dados de entrada necessários para processar o commit da assinatura digital 
	 * em um documento pdf com a Valid Certificadora
	 * @param dto - contém as informações necessárias para validar o commit da assinatura
	 * @return this
	 * */
	public ValidaAssinaturaDocumentoBuilder validarCommitAssinatura(ValidSignDto dto) {
		signDto = dto;
		signDto.setMensagemValidacao(new ArrayList<String>());

		if( moduloSistemaFoiInformado() && parametrosCommitForamInformados() ) {
			
			verificaConexaoRedis();
		}
		
		signDto.setAssinaturaValida(signDto.getMensagemValidacao().isEmpty());
		return this;
	}
	
	/**
	 * Como o serviço de assinatura depende do Redis para armazenar o hash do documento a ser assinado,
	 * o método verifica a disponibilidade do serviço.
	 * @return this
	 * */
	public ValidaAssinaturaDocumentoBuilder verificaConexaoRedis() {
		
		if( !redisService.servicoRedisEstaRodando() ) {
			
			signDto.setAssinaturaValida(false);
			signDto.getMensagemValidacao().add(messages.redisServiceIndisponivel());
		}
		return this;
	}
	
	/**
	 * Valida se o documento submetido para assinatura foi encontrado
	 * O documento pode estar armazenado no file system, está representado por um json em cad-documento
	 * ou ser submetido através de um upload
	 * 
	 * Para os dois primeiros casos o serviço aguarda o idDocumento para efetuar a busca e sendo um upload
	 * aguarda o próprio documento na forma de byte array.
	 * @return this 
	 * */
	public ValidaAssinaturaDocumentoBuilder verificaDocumentoASerAssinado() {
		
		if( !signDto.temIdDocumento() && !signDto.temDocumento() ) {
			
			signDto.setAssinaturaValida(false);
			signDto.getMensagemValidacao().add(messages.documentoParaAssinaturaNaoInformado());
		}
		
		return this;
	} 
	
	/**
	 * Retorna true se o módulo que está consumindo o serviço foi informado
	 * Ex. SIACOL, CORPORATIVO etc
	 * @return true / false
	 * */
	public boolean moduloSistemaFoiInformado() {
		String mensagem = "";
		if( !signDto.temModulo() ) {
			
			signDto.setAssinaturaValida(false);
			mensagem = messages.moduloSistemaNaoInformado();
			signDto.getMensagemValidacao().add(mensagem);			
		}
		
		return mensagem.isEmpty() ? true : false;
	}
	
	/**
	 * Valida se todos os parâmetros necessários na pré assinatura 
	 * extraídos do certificado do assinante foram
	 * informados:
	 * 
	 * - signerCertificate: certificado selecionado pelo cliente usando o ValidHostApp
	 * - thumbprint: impressão digital do certificado selecionado pelo cliente usando o ValidHostApp
	 * 
	 * @return true / false
	 * */
	public boolean parametrosPreSignForamInformados() {
		String mensagem = "";
	
		if( !signDto.temSignerCertificate() ) {
			
			signDto.setAssinaturaValida(false);
			mensagem = messages.certificadoNaoInformado();
			signDto.getMensagemValidacao().add(mensagem);			
		}
		
		if( !signDto.temThumbprint() ) {
			
			signDto.setAssinaturaValida(false);
			mensagem = messages.thumbprintCertificadoNaoInformada();
			signDto.getMensagemValidacao().add(mensagem);			
		}
		
		if( !signDto.temTipoDocumentoAssinatura() ) {
			
			signDto.setAssinaturaValida(false);
			mensagem = messages.tipoDocumentoAssinaturaNaoInformado();
			signDto.getMensagemValidacao().add(mensagem);			
		}
		
		return mensagem.isEmpty() ? true : false;
	}
	
	/**
	 * Valida se todos os parâmetros necessários para o commit da assinatura 
	 * extraídos do certificado do assinante foram informados:
	 * 
	 * - signerCertificate: certificado selecionado pelo cliente usando o ValidHostApp
	 * - signaturePackage: pacote retornado pelo serviço externalPreSignCMS da Valid Certificadora
	 * - signature: assinatura do cliente retornado pelo ValidHostApp
	 * - idDocumento: identificador do documento que está sendo assinado em 'cad-documento'
	 * @return true / false
	 * */
	public boolean parametrosCommitForamInformados() {
		String mensagem = "";
	
		if( !signDto.temSignerCertificate() ) {
			
			signDto.setAssinaturaValida(false);
			mensagem = messages.certificadoNaoInformado();
			signDto.getMensagemValidacao().add(mensagem);			
		}
		
		if( !signDto.temSignature() ) {
			
			signDto.setAssinaturaValida(false);
			mensagem = messages.assinaturaCertificadoNaoInformada();
			signDto.getMensagemValidacao().add(mensagem);			
		}
		
		if( !signDto.temSignaturePackage() ) {
			
			signDto.setAssinaturaValida(false);
			mensagem = messages.signaturePackageNaoInformado();
			signDto.getMensagemValidacao().add(mensagem);			
		}
		
		if( !signDto.temChaveAssinaturaRedis() ) {
			
			signDto.setAssinaturaValida(false);
			mensagem = messages.chaveRedisAssinaturaNaoLocalizada();
			signDto.getMensagemValidacao().add(mensagem);			
		}
		
		if( !signDto.temIdDocumento() ) {
			
			signDto.setAssinaturaValida(false);
			mensagem = messages.documentoAssinaturaNaoInformado();
			signDto.getMensagemValidacao().add(mensagem);			
		}
		
		return mensagem.isEmpty() ? true : false;
	}
	
	/** 
	 *  Retorna o objeto signDto contendo as mensagens de validação 
	 *  sobre o objeto que está em validação
	 *  @return signDto
	 */
	public ValidSignDto build() {
		return signDto;
	}
}
