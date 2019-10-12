package br.org.crea.commons.builder.protocolo.validaterules;

import java.util.ArrayList;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.protocolo.EprocessoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.dao.protocolo.RlAutorizacaoProtocoloDao;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.interfaceutil.FormatMensagensConsumer;
import br.org.crea.commons.models.commons.Eprocesso;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.models.protocolo.dtos.SubstituicaoProtocoloDto;

public class ValidateSubstituicaoProtocoloBuilder {

	@Inject ProtocoloDao protocoloDao;
	
	@Inject EprocessoDao eprocessoDao;

	@Inject RlAutorizacaoProtocoloDao autorizacaoDao;
	
	@Inject ProtocoloConverter protocoloConverter;

	@Inject HelperMessages messages;
	
	@Inject FormatMensagensConsumer formatMensagensValidacao;
	
	private SubstituicaoProtocoloDto substituicaoDto;
	
	/**
	 * 
	 * Método que valida o estado dos protocolos envolvidos na substituição (substituto e substituido)
	 * 
	 * O dto da classe SubstituicaoProtocoloDto contém o número do protocolo substituto e do que será substituido.
	 * A partir desta referência estamos buscando ambos os protocolos e caso existam as demais validações de regras serão verificadas.
	 * 
	 * Se um dos protocolos não forem encontrados o recurso notificará a ausência do protocolo e vai interromper o 
	 * processo.
	 * 
	 * @param dto - contém protocolo substituto e protocolo a ser substituído.
	 * @param usuario - usuário da ação, autenticado pela aplicação.
	 * @return this - retorna  a própria classe com as validações dos protocolos da substituição.
	 * 
	 * **/
	public ValidateSubstituicaoProtocoloBuilder validarSubstituicao(SubstituicaoProtocoloDto dto, UserFrontDto user) {
		
		substituicaoDto = dto;
		substituicaoDto.setMensagensSubstituicao(new ArrayList<String>());
		
		if( protocolosDaSubstituicaoExistem() && moduloSistemaFoiInformado() ) {
			
			validaFuncionarioDaSubstituicao(user)
			.validarEstadoProtocolos()
			.validaPermissaoFuncionario()
			.validaNaturezaProtocolos()
			.validaLiberacaoDocflow();
		}
		formatMensagensValidacao.accept(substituicaoDto.getMensagensSubstituicao());
		return this;
	}
	
	/**
	 * Seta as informações do usuário da substituição para o objeto que será retornado.
	 * @param usuario
	 * @return this
	 * **/
	public ValidateSubstituicaoProtocoloBuilder validaFuncionarioDaSubstituicao(UserFrontDto usuario) {
		
		FuncionarioDto funcionarioDaSubstituicao = new FuncionarioDto();
		funcionarioDaSubstituicao.setId(usuario.getIdFuncionario());
		funcionarioDaSubstituicao.setIdPessoa(usuario.getIdPessoa());
		funcionarioDaSubstituicao.setMatricula(usuario.getMatricula());
		funcionarioDaSubstituicao.setNome(usuario.getNome());
		funcionarioDaSubstituicao.setIdDepartamento(Long.parseLong(usuario.getDepartamento().getId()));
		substituicaoDto.setFuncionarioDaSubstituicao(funcionarioDaSubstituicao);
		return this;
	}
	/**
	
	 * Verifica o web service do Docflow está operante e se
	 * o serviço de substituição está liberado no Docflow. 
	 * @return this
	 * */
	public ValidateSubstituicaoProtocoloBuilder validaLiberacaoDocflow() {
		
		ProtocoloDto substituido = substituicaoDto.getProtocoloSubstituido();
		ProtocoloDto substituto = substituicaoDto.getProtocoloSubstituto();
		
		if ( substituto.isDigital() || substituido.isDigital() ) {
			
			Eprocesso eprocesso = eprocessoDao.getBy(1L);
			
			if( !eprocesso.estaLiberado() ) {
				
				substituicaoDto.setPossuiErrosSubstituicao(true);
				substituicaoDto.getMensagensSubstituicao().add(messages.eprocessoBloqueado());
			}
			
			if( !eprocesso.podeSubstituir() ) {
				substituicaoDto.setPossuiErrosSubstituicao(true);
				substituicaoDto.getMensagensSubstituicao().add(messages.eprocessoBloqueadoParaSubstituir()); 
			}
		}
		
		return this;
	}
	
	/**
	 * Valida o estado dos protocolos: se recebido, excluido, substituido, digitalizado
	 * @return this
	 * */
	public ValidateSubstituicaoProtocoloBuilder validarEstadoProtocolos() {
		
		verificaExclusaoProtocolos();
		verificaRecebimentoProtocolos();
		verificaStatusProtocolos();
		verificaSubstituicaoProtocolos();
		verificaDepartamentoProtocolos();
		verificaIdentidadeProtocolos();
		return this;
	}
	
	/**
	 * Verifica se protocolo substituido e protocolo substituto estão digitalizados
	 * A regra não permite que protocolo físico seja substituto de protocolo digital ou vice e versa.
	 * @return this
	 * **/
	public ValidateSubstituicaoProtocoloBuilder validaNaturezaProtocolos() {
		
		ProtocoloDto substituido = substituicaoDto.getProtocoloSubstituido();
		ProtocoloDto substituto = substituicaoDto.getProtocoloSubstituto();
		
		if ( substituto.isDigital() != substituido.isDigital() ) {
		
			substituicaoDto.setPossuiErrosSubstituicao(true);
			if( substituido.isDigital() ) {
				
				substituicaoDto.getMensagensSubstituicao().add( messages.protocoloSubstituidoDigitalizado());
				
			} else {
				
				substituicaoDto.getMensagensSubstituicao().add( messages.protocoloSubstitutoDigitalizado());
			}
		}
		return this;
	}
	
	/**
	 * Verifica a permissão do funcionário para substituir protocolo.
	 * Se não for o administrador OU não tiver permissão para substituição, somente poderá substituir se 
	 * o funcionário for o responsável pelo cadastro e se o protocolo substituido só tiver um movimento
	 * @return this
	 * **/
	public ValidateSubstituicaoProtocoloBuilder validaPermissaoFuncionario() {

		ProtocoloDto substituido = substituicaoDto.getProtocoloSubstituido();
		FuncionarioDto funcionario = substituicaoDto.getFuncionarioDaSubstituicao();
		boolean podeSubstituir = autorizacaoDao.podeSubstituirProtocolo(funcionario.getId());
		
		if(!podeSubstituir) {
			
			if(!protocoloSubstituidoFoiCadastradoPeloFuncionario()) {
				
				substituicaoDto.setPossuiErrosSubstituicao(true);
				substituicaoDto.getMensagensSubstituicao().add(messages.permissaoSubstituirProtocolo(funcionario.getNome(), substituido.getNumeroProtocolo()));
			}
		}
		return this;
	}
	
	/**
	 * Retorna true se o módulo que está operando a substituição foi informado
	 * Ex. SIACOL, CORPORATIVO etc
	 * @return true / false
	 * */
	public boolean moduloSistemaFoiInformado() {
		String mensagem = "";
		if( substituicaoDto.getModuloSistema() == null ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			mensagem = messages.moduloSistemaNaoInformado();
			substituicaoDto.getMensagensSubstituicao().add(mensagem);			
		}
		
		return mensagem.isEmpty() ? true : false;
	}
	
	/**
	 * Busca os protocolos da substituição na base e seta mensagem caso eles não existam.
	 * Retorna true se os protocolos da substituição forem encontrados na base.
	 * @return true /false
	 * */
	public boolean protocolosDaSubstituicaoExistem() {
		
		boolean substitutoEncontrado = true;
		boolean substituidoEncontrado = true;
		
		Long numeroSubstituido = substituicaoDto
		 	.optionalProtocoloSubstituido()
		 	.map(ProtocoloDto::getNumeroProtocolo)
		 	.get();
		
		Long numeroSubstituto = substituicaoDto
			.optionalProtocoloSubstituto()
			.map(ProtocoloDto::getNumeroProtocolo)
			.get();
			
		substituicaoDto.setProtocoloSubstituido(protocoloConverter.toDto(protocoloDao.getProtocoloBy(numeroSubstituido)));
		substituicaoDto.setProtocoloSubstituto(protocoloConverter.toDto(protocoloDao.getProtocoloBy(numeroSubstituto)));

		if( substituicaoDto.getProtocoloSubstituto() == null ) {
			
			substitutoEncontrado = false;
			substituicaoDto.setPossuiErrosSubstituicao(true);
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloSubstitutoNaoEncontrado(numeroSubstituido));
		}
		
		if( substituicaoDto.getProtocoloSubstituido() == null ) {
			
			substitutoEncontrado = false;
			substituicaoDto.setPossuiErrosSubstituicao(true);
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloSubstituidoNaoEncontrado(numeroSubstituido));
		}
		
		return !substitutoEncontrado || !substituidoEncontrado ? false : true; 
	}
	
	/**
	 * Verifica se o status da transação dos protocolos está com divergência, ou seja, 
	 * se houve uma tentativa de operar com o protocolo e a ação não foi concretizada.
	 * Ex. pendente tramitação, recebimento, anexação ou demais ações do protocolo.
	 * */
	public void verificaStatusProtocolos() {
		
		ProtocoloDto substituido = substituicaoDto.getProtocoloSubstituido();
		ProtocoloDto substituto = substituicaoDto.getProtocoloSubstituto();
		
		if( substituido.statusTransacaoEstaDivergente() ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			String status = substituido.getStatusTransacao();
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloSubstituidoStatusTransacao(substituido.getNumeroProtocolo(), status));
		}
		
		if( substituto.statusTransacaoEstaDivergente() ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			String status = substituto.getStatusTransacao();
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloSubstitutoStatusTransacao(substituto.getNumeroProtocolo(), status));
			
		}
	}
	
	/**
	 * Verifica se protocolos da substituição estão recebidos.
	 * **/
	public void verificaRecebimentoProtocolos() {
		
		ProtocoloDto substituido = substituicaoDto.getProtocoloSubstituido();
		ProtocoloDto substituto = substituicaoDto.getProtocoloSubstituto();
		
		if( !substituido.getUltimoMovimento().estaRecebido() ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloNaoRecebido(substituido.getNumeroProtocolo()));
		}
		
		if( !substituto.getUltimoMovimento().estaRecebido() ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloNaoRecebido(substituto.getNumeroProtocolo()));
		}
	}
	
	/**
	 * Verifica se protocolos da substituição estão excluidos.
	 * */
	public void verificaExclusaoProtocolos() {
		ProtocoloDto substituido = substituicaoDto.getProtocoloSubstituido();
		ProtocoloDto substituto = substituicaoDto.getProtocoloSubstituto();
		
		if( substituido.estaExcluido() ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloExcluido(substituido.getNumeroProtocolo()));
		}
		
		if( substituto.estaExcluido() ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloExcluido(substituto.getNumeroProtocolo()));
		}
	}
	
	/**
	 * Verifica se protocolos da substituição já estão substituidos.
	 * */
	public void verificaSubstituicaoProtocolos() {
		ProtocoloDto substituido = substituicaoDto.getProtocoloSubstituido();
		ProtocoloDto substituto = substituicaoDto.getProtocoloSubstituto();
		
		if( substituido.estaSubstituido() ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloSubstituido(substituido.getNumeroProtocolo()));
		}
		
		if( substituto.estaSubstituido() ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloSubstituido(substituto.getNumeroProtocolo()));
		}
	}
	
	/**
	 * Verifica se os protocolos são do mesmo departamento
	 * */
	public void verificaDepartamentoProtocolos() {
		String mensagem = "";
		
		Long destinoProtocoloSubstituido = substituicaoDto.getProtocoloSubstituido().getUltimoMovimento().getIdDepartamentoDestino(); 
				
		Long destinoProtocoloSusbtituto = substituicaoDto.getProtocoloSubstituto().getUltimoMovimento().getIdDepartamentoDestino();
		if( !destinoProtocoloSubstituido.equals(destinoProtocoloSusbtituto) ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			mensagem = messages.protocoloDepartamentoDiferente(substituicaoDto.getProtocoloSubstituido().getNumeroProtocolo(), substituicaoDto.getProtocoloSubstituto().getNumeroProtocolo());
			substituicaoDto.getMensagensSubstituicao().add(mensagem);
		}
	}
	
	/**
	 * Verifica se o protocolo informado para ser substituto não é o próprio substituido
	 * */
	public void verificaIdentidadeProtocolos() {
		ProtocoloDto substituido = substituicaoDto.getProtocoloSubstituido();
		ProtocoloDto substituto = substituicaoDto.getProtocoloSubstituto();
		
		if( substituido.getNumeroProtocolo().equals(substituto.getNumeroProtocolo()) ) {
			
			substituicaoDto.setPossuiErrosSubstituicao(true);
			substituicaoDto.getMensagensSubstituicao().add(messages.protocoloSubstitutoIgualAoSubstituido());
		}
	}
	
	/**
	 * Verifica se o protocolo substituido foi cadastrado pelo funcionário e se
	 * o protocolo não foi movimentado, ou seja, só possui um movimento (o inicial)
	 * **/
	public boolean protocoloSubstituidoFoiCadastradoPeloFuncionario() {
		
		ProtocoloDto substituido = substituicaoDto.getProtocoloSubstituido();
		FuncionarioDto funcionarioDaSubstituicao = new FuncionarioDto();
		
		if(substituido.getIdFuncionario().equals(funcionarioDaSubstituicao.getId()) &&
				substituido.getPrimeiroMovimento().getId().equals(substituido.getUltimoMovimento().getId())) {
			
			return true;
		}
		
		return false;
	}
	
	/** 
	 *  Retorna o objeto substituicaoDto contendo as mensagens de validação 
	 *  sobre os protocolos da substituição
	 *  @return substituicaoDto
	 */
	public SubstituicaoProtocoloDto build() {
		return substituicaoDto;
	}
}
