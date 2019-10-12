package br.org.crea.commons.builder.protocolo.validaterules;

import java.util.ArrayList;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.interfaceutil.FormatMensagensConsumer;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;

public class ValidaApensoProtocoloBuilder {
	
	@Inject ProtocoloDao protocoloDao;

	@Inject ProtocoloConverter protocoloConverter;

	@Inject HelperMessages messages;

	@Inject ValidaGenericJuntadaProtocoloBuilder validateGenericJuntada;

	@Inject FormatMensagensConsumer formatMensagensValidacao;

	private JuntadaProtocoloDto apensacaoDto;
	
	public ValidaApensoProtocoloBuilder validarApensacao(JuntadaProtocoloDto dto, UserFrontDto usuario) {
		
		validateGenericJuntada.setFuncionarioDaJuntada(usuario, dto);
		apensacaoDto = dto;
		
		apensacaoDto.setMensagensJuntada(new ArrayList<String>());
		boolean tipoJuntadaFoiInformada = validateGenericJuntada.tipoJuntadaFoiInformada(apensacaoDto);
		boolean moduloSistemaFoiInformado = validateGenericJuntada.moduloSistemaFoiInformado(apensacaoDto);
		
		if( protocolosApensacaoExistem() && tipoJuntadaFoiInformada && moduloSistemaFoiInformado ) {
			
			validateGenericJuntada.validarEstadoProtocolosParaJuntada(apensacaoDto);
			validateGenericJuntada.validarDepartamentoProtocolos(apensacaoDto);
			validarJuntadaProtocoloEletronico();
			formatMensagensValidacao.accept(apensacaoDto.getMensagensJuntada());
		}
		
		return this;
	}
	
	/**
	 * Retorna true caso os protocolos da apensação existam.
	 * @return true /false
	 * */
	public boolean protocolosApensacaoExistem() {
		setMensagemBuscaProtocolosApensacao();
		return 	apensacaoDto.getMensagensJuntada().isEmpty() ? true : false;
	}
	
	/**
	 * Busca os protocolos da apensação na base e retorna mensagem caso eles não existam.
	 * @return mensagem
	 * */
	public void setMensagemBuscaProtocolosApensacao() {
	
		apensacaoDto.setProtocoloPrincipal(protocoloConverter.toDto(protocoloDao.getProtocoloBy(apensacaoDto.getProtocoloPrincipal().getNumeroProtocolo())));
		apensacaoDto.setProtocoloDaJuntada(protocoloConverter.toDto(protocoloDao.getProtocoloBy(apensacaoDto.getProtocoloDaJuntada().getNumeroProtocolo())));
		
		if( apensacaoDto.getProtocoloPrincipal() == null ) {
			
			apensacaoDto.setPossuiErrosNaJuntada(true);
			apensacaoDto.getMensagensJuntada().add(messages.protocoloPrincipalNaoEncontrado());
		}
		
		if( apensacaoDto.getProtocoloDaJuntada() == null ) {
			
			apensacaoDto.setPossuiErrosNaJuntada(true);
			apensacaoDto.getMensagensJuntada().add(messages.protocoloApensoNaoEncontrado());
		}
	}
	
	/**
	 * Valida a consistência dos dados dos protocolos para efetuar a apensação no Docflow
	 * caso os protocolos sejam eletrônicos.
	 * @return this
	 * */
	public ValidaApensoProtocoloBuilder validarJuntadaProtocoloEletronico() {
		
		ProtocoloDto principal = apensacaoDto.getProtocoloPrincipal();
		ProtocoloDto apenso = apensacaoDto.getProtocoloDaJuntada();
		
		if ( principal.isDigital() || apenso.isDigital() ) {
			
			validateGenericJuntada.validarDesbloqueioEprocesso(apensacaoDto);
			validateGenericJuntada.setMensagemLocalizacaoEprocesso(apensacaoDto);
			validateGenericJuntada.setMensagemFuncionarioJuntadaProtocoloDigital(apensacaoDto);
		}
		return this;
	}
	
	/** 
	 *  Retorna o objeto apensacaoDto contendo as mensagens de validação 
	 *  sobre o protocolo principal e o protocolo a ser apensado
	 *  @return apensacaoDto
	 */
	public JuntadaProtocoloDto build() {
		return apensacaoDto;
	}

}
