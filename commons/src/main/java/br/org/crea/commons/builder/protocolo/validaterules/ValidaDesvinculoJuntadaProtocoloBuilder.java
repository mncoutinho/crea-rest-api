package br.org.crea.commons.builder.protocolo.validaterules;

import java.util.ArrayList;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.interfaceutil.FormatMensagensConsumer;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.protocolo.enuns.TipoJuntadaProtocoloEnum;

public class ValidaDesvinculoJuntadaProtocoloBuilder {

	@Inject ProtocoloDao protocoloDao;

	@Inject FuncionarioDao funcionarioDao;
	
	@Inject ProtocoloConverter protocoloConverter;

	@Inject HelperMessages messages;

	@Inject ValidaGenericJuntadaProtocoloBuilder validateGenericJuntada;

	@Inject FormatMensagensConsumer formatMensagensValidacao;
	
	private JuntadaProtocoloDto desvinculoProtocoloDto;
	
	/**
	 * 
	 * Método que valida o estado dos protocolos da desanexação.
	 * 
	 * O dto da classe JuntadaProtocoloDto contém o número do protocolo que será desanexado.
	 * A partir desta referência estamos buscando o protocolo e caso exista as demais validações de regras serão verificadas.
	 * 
	 * @param dto - contém protocolos da desanexacao, a informação do módulo do sistema que está acessando o recurso e 
	 * 				o tipo de ação relacionada a juntada do protocolo.
	 * @param usuario - usuário da ação, autenticado pela aplicação.
	 * @return this - retorna  a própria classe com as validações dos protocolos da desanexação.
	 * 
	 * **/
	
	public ValidaDesvinculoJuntadaProtocoloBuilder validarDesvinculo(JuntadaProtocoloDto dto, UserFrontDto usuario) {

		validateGenericJuntada.setFuncionarioDaJuntada(usuario, dto);
		desvinculoProtocoloDto = dto;
		
		desvinculoProtocoloDto.setMensagensJuntada(new ArrayList<String>());
		boolean tipoJuntadaFoiInformada = validateGenericJuntada.tipoJuntadaFoiInformada(desvinculoProtocoloDto);
		boolean moduloSistemaFoiInformado = validateGenericJuntada.moduloSistemaFoiInformado(desvinculoProtocoloDto);

		if( protocolosDesvinculoExistem() && tipoJuntadaFoiInformada && moduloSistemaFoiInformado ) {
			
			if( protocoloEstaVinculadoPrincipal() ) {
				
				 validarEstadoProtocolos().validarPermissaoFuncionarioDesvinculo().validarDesvinculoProtocoloEletronico();
				 validateGenericJuntada.setMensagemPermissaoUnidadeProtocolo(desvinculoProtocoloDto);
				 formatMensagensValidacao.accept(desvinculoProtocoloDto.getMensagensJuntada());
			}
		}
		return this;
	}
	
	/**
	 * Busca os protocolos da desanexação na base e retorna mensagem caso eles não existam.
	 * */
	public void setMensagemBuscaProtocoloDesanexacao() {
		
		desvinculoProtocoloDto.setProtocoloPrincipal(protocoloConverter.toDto(protocoloDao.getProtocoloBy(desvinculoProtocoloDto.getProtocoloPrincipal().getNumeroProtocolo())));
		desvinculoProtocoloDto.setProtocoloDaJuntada(protocoloConverter.toDto(protocoloDao.getProtocoloBy(desvinculoProtocoloDto.getProtocoloDaJuntada().getNumeroProtocolo())));
		
		if( desvinculoProtocoloDto.getProtocoloPrincipal() == null ) {
			
			desvinculoProtocoloDto.setPossuiErrosNaJuntada(true);
			desvinculoProtocoloDto.getMensagensJuntada().add(messages.protocoloPrincipalNaoEncontrado());
		}
		
		if( desvinculoProtocoloDto.getProtocoloDaJuntada() == null ) {
			
			desvinculoProtocoloDto.setPossuiErrosNaJuntada(true);
			desvinculoProtocoloDto.getMensagensJuntada().add(messages.protocoloAnexoNaoEncontrado());
		}
		
	}
	
	/**
	 * Retorna true caso o protocolo da desanexação exista.
	 * @return true /false
	 * */
	public boolean protocolosDesvinculoExistem() {
		setMensagemBuscaProtocoloDesanexacao();
		return 	desvinculoProtocoloDto.getMensagensJuntada().isEmpty() ? true : false;
	}
	
	/**
	 * Retorna true se a dataDigitalizacao do protocolo a ser desanexado está preenchida.
	 * Estando preenchida significa que o protocolo era físico e foi digitalizado posteriormente.
	 * @return true / false 
	 * */
	private boolean protocoloASerDesanexadoEstaVinculaAProcessoQueNasceuFisico(){
		return desvinculoProtocoloDto.getProtocoloPrincipal().getDataDigitalizacao() != null ? true : false; 
	}
	
	/**
	 * Verifica se o funcionario está autorizado a desanexar, procurando pela permissão 'desanexar protocolo'
	 * e 'inventariar departamento'
	 * @return this 
	 * */
	public ValidaDesvinculoJuntadaProtocoloBuilder validarPermissaoFuncionarioDesvinculo() {
	
		Long permissao = desvinculoProtocoloDto.getTipoJuntadaProtocolo().equals(TipoJuntadaProtocoloEnum.DESANEXACAO) ? new Long(3) : new Long(4); 
		boolean permissaoDesvincular = funcionarioDao.temPermissaoProtocolo(desvinculoProtocoloDto.getFuncionarioDaJuntada().getId(), permissao);
		boolean permissaoInventariarDepartamento = funcionarioDao.temPermissaoProtocolo(desvinculoProtocoloDto.getFuncionarioDaJuntada().getId(), new Long(1));
		
		if( !permissaoDesvincular && !permissaoInventariarDepartamento ) {
			desvinculoProtocoloDto.setPossuiErrosNaJuntada(true);
			desvinculoProtocoloDto.getMensagensJuntada().add(messages.permissaoFuncionarioDesvincular(desvinculoProtocoloDto.getFuncionarioDaJuntada().getNome()));
		}
		return this;
	}
	
	/**
	 * Valida o estado do protocolo: se possui status divergente, recebido, inventariado
	 * @return this
	 * */
	public ValidaDesvinculoJuntadaProtocoloBuilder validarEstadoProtocolos() {
		
		validateGenericJuntada.setMensagemStatusDivergente(desvinculoProtocoloDto);
		validateGenericJuntada.setMensagemProtocoloMarcadoInventario(desvinculoProtocoloDto);
		validateGenericJuntada.setMensagemProtocoloNaoRecebido(desvinculoProtocoloDto);
		
		return this;
	}
	
	/**
	 * Valida se o protocolo a ser desanexado foi mesmo juntado ao principal
	 * @return this
	 * */
	public boolean protocoloEstaVinculadoPrincipal() {
		

		Long protocoloADesvincular = desvinculoProtocoloDto.getProtocoloDaJuntada().getNumeroProtocolo();
		Long protocoloPrincipal = desvinculoProtocoloDto.getProtocoloPrincipal().getNumeroProtocolo();
		
		if( !protocoloDao.protocoloEstaJuntadoAoProtocoloPrincipal(protocoloPrincipal, protocoloADesvincular) ) {
		
			desvinculoProtocoloDto.setPossuiErrosNaJuntada(true);
			desvinculoProtocoloDto.getMensagensJuntada().add(messages.desvincularProtocoloJuntada(protocoloPrincipal, protocoloADesvincular));
			return false;
		}
		
		return true;
	}
	

	/**
	 * Valida a localização dos protocolos e permissão do funcionário dentro da unidade.
	 * @return this
	 * */
	public ValidaDesvinculoJuntadaProtocoloBuilder validarDepartamentoProtocolos() {
		
		validateGenericJuntada.setMensagemPermissaoUnidadeProtocolo(desvinculoProtocoloDto);
		return this;
	}
	
	/**
	 * Valida a consistência dos dados dos protocolos para efetuar a anexação no Docflow
	 * caso os protocolos sejam eletrônicos.
	 * @return this
	 * */
	public ValidaDesvinculoJuntadaProtocoloBuilder validarDesvinculoProtocoloEletronico() {
		
		ProtocoloDto principal = desvinculoProtocoloDto.getProtocoloPrincipal();
		ProtocoloDto vinculado = desvinculoProtocoloDto.getProtocoloDaJuntada();
		
		if ( principal.isDigital() || vinculado.isDigital() ) {
			
			setMensagemProtocoloPrincipalOrigemFisica();
			validateGenericJuntada.validarDesbloqueioEprocesso(desvinculoProtocoloDto);
			validateGenericJuntada.setMensagemLocalizacaoEprocesso(desvinculoProtocoloDto);
			validateGenericJuntada.setMensagemFuncionarioJuntadaProtocoloDigital(desvinculoProtocoloDto);
		}
		
		return this;
	}
	
	
	/**
	 * Verifica se o protocolo principal era físico e está digitalizado.
	 * Se for o caso, não será permitido desanexar.
	 * */
	public void setMensagemProtocoloPrincipalOrigemFisica() {
		
		Long protocoloDesvincular = desvinculoProtocoloDto.getProtocoloDaJuntada().getNumeroProtocolo();
		if( protocoloASerDesanexadoEstaVinculaAProcessoQueNasceuFisico()) {
		
			desvinculoProtocoloDto.setPossuiErrosNaJuntada(true);
			desvinculoProtocoloDto.getMensagensJuntada().add(messages.desanexarProtocoloVinculadoProtocoloOrigemFisico(protocoloDesvincular));
		}
	}

	/** Remove as mensagens vazias da desanexacao
	 * @return this
	 */
	public ValidaDesvinculoJuntadaProtocoloBuilder validarMensagensAnexacao() {
		
		formatMensagensValidacao.accept(desvinculoProtocoloDto.getMensagensJuntada());
		return this;
	}
	
	/** 
	 *  Retorna o objeto desanexacaoDto contendo as mensagens de validação 
	 *  sobre o protocolo principal e o protocolo a ser desanexado
	 *  @return anexacaoDto
	 */
	public JuntadaProtocoloDto build() {
		return desvinculoProtocoloDto;
	}
}
