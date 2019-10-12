package br.org.crea.commons.builder.protocolo.validaterules;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.interfaceutil.FormatMensagensConsumer;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;
import br.org.crea.commons.util.DateUtils;

public class ValidaAnexoProtocoloBuilder {
	
	@Inject ProtocoloDao protocoloDao;

	@Inject ProtocoloConverter protocoloConverter;

	@Inject HelperMessages messages;

	@Inject ValidaGenericJuntadaProtocoloBuilder validateGenericJuntada;

	@Inject FormatMensagensConsumer formatMensagensValidacao;

	private JuntadaProtocoloDto anexacaoDto;
	
	/**
	 * 
	 * Método que valida o estado do protocolo principal (que receberá o anexo) e o estado
	 * do protocolo a ser anexado.
	 * 
	 * O dto da classe JuntadaProtocoloDto contém o número do protocolo principal e do que será anexado.
	 * A partir desta referência estamos buscando ambos os protocolos e caso existam as demais validações de regras serão verificadas.
	 * 
	 * @param dto - contém protocolo principal e protocolo a ser anexado.
	 * @param usuario - usuário da ação, autenticado pela aplicação.
	 * @return this - retorna  a própria classe com as validações dos protocolos da anexação.
	 * 
	 * **/
	
	public ValidaAnexoProtocoloBuilder validarAnexacao(JuntadaProtocoloDto dto, UserFrontDto usuario) {

		validateGenericJuntada.setFuncionarioDaJuntada(usuario, dto);
		anexacaoDto = dto;
		
		anexacaoDto.setMensagensJuntada(new ArrayList<String>());
		boolean tipoJuntadaFoiInformada = validateGenericJuntada.tipoJuntadaFoiInformada(anexacaoDto);
		boolean moduloSistemaFoiInformado = validateGenericJuntada.moduloSistemaFoiInformado(anexacaoDto);
		
		if( protocolosDaAnexacaoExistem() && tipoJuntadaFoiInformada && moduloSistemaFoiInformado ) {
			
			 validateGenericJuntada.validarEstadoProtocolosParaJuntada(anexacaoDto);
			 validateGenericJuntada.validarDepartamentoProtocolos(anexacaoDto);
			 validarVinculoProtocolos().validarTipoProtocolo().validarJuntadaProtocoloEletronico();
			 formatMensagensValidacao.accept(anexacaoDto.getMensagensJuntada());
		}
		
		return this;
		
	}
	
	/**
	 * Valida o vinculo entre os protocolos da anexação. Verifica o vínculo quanto ao processo e ao interessado
	 * @return this
	 * */
	public ValidaAnexoProtocoloBuilder validarVinculoProtocolos() {
		
		setMensagemProcessosDiferentes();
		setMensagemProcessoInicial();
		setMensagemAnexoDesvinculadoDoPrincipal();
		setMensagemInteressadoProtocolos();
		return this;
	}
	
	/**
	 * De acordo com tipo do protocolo (TipoProtocoloEnum) e processo, valida a
	 * permissão para anexar.
	 * @return this
	 * */
	public ValidaAnexoProtocoloBuilder validarTipoProtocolo() {
		
		setMensagemAnexacaoIndevida();
		setMensagemPermissaoParaReceberAnexo();
		setMensagemProtocolosTipoSete();
		setMensagemTransacaoAnexo();
		return this;
	}
	
	/**
	 * Valida a consistência dos dados dos protocolos para efetuar a anexação no Docflow
	 * caso os protocolos sejam eletrônicos.
	 * @return this
	 * */
	public ValidaAnexoProtocoloBuilder validarJuntadaProtocoloEletronico() {
		
		ProtocoloDto principal = anexacaoDto.getProtocoloPrincipal();
		ProtocoloDto anexo = anexacaoDto.getProtocoloDaJuntada();
		
		if ( principal.isDigital() || anexo.isDigital() ) {
			
			validateGenericJuntada.validarDesbloqueioEprocesso(anexacaoDto);
			validateGenericJuntada.setMensagemLocalizacaoEprocesso(anexacaoDto);
			validateGenericJuntada.setMensagemFuncionarioJuntadaProtocoloDigital(anexacaoDto);
			setMensagemOrdemCronologicaAnexacao();
		}
		
		return this;
	}
	
	/**
	 * Retorna true caso os protocolos da anexação existam.
	 * @return true /false
	 * */
	public boolean protocolosDaAnexacaoExistem() {
		setMensagemBuscaProtocolosDaAnexacao();
		return 	anexacaoDto.getMensagensJuntada().isEmpty() ? true : false;
	}
	
	/**
	 * Busca os protocolos da anexação na base e retorna mensagem caso eles não existam.
	 * */
	public void setMensagemBuscaProtocolosDaAnexacao() {
	
		anexacaoDto.setProtocoloPrincipal(protocoloConverter.toDto(protocoloDao.getProtocoloBy(anexacaoDto.getProtocoloPrincipal().getNumeroProtocolo())));
		anexacaoDto.setProtocoloDaJuntada(protocoloConverter.toDto(protocoloDao.getProtocoloBy(anexacaoDto.getProtocoloDaJuntada().getNumeroProtocolo())));
		
		if( anexacaoDto.getProtocoloPrincipal() == null ) {
			
			anexacaoDto.setPossuiErrosNaJuntada(true);
			anexacaoDto.getMensagensJuntada().add(messages.protocoloPrincipalNaoEncontrado());
		}
		
		if( anexacaoDto.getProtocoloDaJuntada() == null ) {
			
			anexacaoDto.setPossuiErrosNaJuntada(true);
			anexacaoDto.getMensagensJuntada().add(messages.protocoloAnexoNaoEncontrado());
		}
	}
	
	/**
	 * Valida o tipo de protocolo e processo para determinar se a tentativa do usuário de anexar 
	 * não deveria ser de apensar.
	 * */
	public void setMensagemAnexacaoIndevida() {
		
		TipoProtocoloEnum tipoProtocoloAnexo = anexacaoDto.getProtocoloDaJuntada().getTipoProtocolo(); 
		Long numeroProcessoAnexo = anexacaoDto.getProtocoloDaJuntada().getNumeroProcesso();
		
		if( tipoProtocoloAnexo.equals(TipoProtocoloEnum.PROTOCOLO) && numeroProcessoAnexo.equals(new Long(0)) ){

			anexacaoDto.setPossuiErrosNaJuntada(true);
			anexacaoDto.getMensagensJuntada().add(messages.protocoloAnexacaoIndevida());
		}
	}
	
	/**
	 * Valida se ambos protocolos são do mesmo processo.
	 * Somente estão fora da regra os protocolos que são de abertura de tomo gerados a partir do
	 * processo inicial. Quando o processo inicial gera tomo, os demais protocolos serão anexados ao protocolo que gerou o tomo. 
	 * */
	public void setMensagemProcessosDiferentes() {
		
		if( !(anexacaoDto.getProtocoloPrincipal().getNumeroProcesso().equals(anexacaoDto.getProtocoloDaJuntada().getNumeroProcesso()))    
			 && (!assuntoProtocolosEhAberturaTomo()) ) {

			anexacaoDto.setPossuiErrosNaJuntada(true);
			anexacaoDto.getMensagensJuntada().add(messages.protocolosProcessosDiferentes());
		}
	}
	
	/**
	 * Verifica se o anexo sendo um tipo 7 vinculado a um processo, está sendo anexado ao processo que ele pertence.
	 * */
	public void setMensagemAnexoDesvinculadoDoPrincipal() {
		
		ProtocoloDto anexo = anexacaoDto.getProtocoloDaJuntada();
		ProtocoloDto principal = anexacaoDto.getProtocoloPrincipal();
		
		if( anexo.getTipoProtocolo().equals(TipoProtocoloEnum.PROTOCOLO) && !anexo.getNumeroProcesso().equals(new Long(0)) ) {

			if( !principal.getNumeroProtocolo().equals(anexo.getNumeroProcesso()) 
				&& !principal.getDataEmissao().before(getDataCorteNasRegrasParaProtocoloAntigo())
				&& !assuntoProtocolosEhAberturaTomo() ) {

				anexacaoDto.setPossuiErrosNaJuntada(true);
				anexacaoDto.getMensagensJuntada().add(messages.anexoDesvinculadoDoPrincipal(anexo.getNumeroProcesso()));
			}
		}
	}
	
	
	/**
	 * Verifica se ambos protocolos são tipo 7, pois um protocolo desta natureza só pode receber protocolo
	 * do mesmo tipo.
	 * */
	public void setMensagemProtocolosTipoSete() {
		
		ProtocoloDto anexo = anexacaoDto.getProtocoloDaJuntada();
		ProtocoloDto principal = anexacaoDto.getProtocoloPrincipal();
		
		if( principal.getTipoProtocolo().equals(TipoProtocoloEnum.PROTOCOLO) && !anexo.getTipoProtocolo().equals(TipoProtocoloEnum.PROTOCOLO)) {

			anexacaoDto.setPossuiErrosNaJuntada(true);
			anexacaoDto.getMensagensJuntada().add(messages.protocolosAnexacaoTipoSete());
		}
	}
	
	/**
	 * Verifica se o protocolo principal pode receber anexo de acordo com o tipo.
	 * */
	public void setMensagemPermissaoParaReceberAnexo() {
		
		if( !protocoloPodeReceberAnexo() ) {

			anexacaoDto.setPossuiErrosNaJuntada(true);
			anexacaoDto.getMensagensJuntada().add(messages.permissaoProtocoloParaReceberAnexo());
		}
	}
	
	
	/**
	 * Verifica se o protocolo principal é considerado o processo inicial, a premissa é que
	 * o número seja iqual ao processo. Estão fora da regra protocolos antigos e abertura de tomo 
	 *  */
	public void setMensagemProcessoInicial() {
		
		ProtocoloDto principal = anexacaoDto.getProtocoloPrincipal();
		
		if( !principal.getNumeroProtocolo().equals(principal.getNumeroProcesso())
			&& !principal.getDataEmissao().before(getDataCorteNasRegrasParaProtocoloAntigo())
			&& !assuntoProtocolosEhAberturaTomo() ) {
			
			anexacaoDto.setPossuiErrosNaJuntada(true);
			anexacaoDto.getMensagensJuntada().add(messages.processoInicialAnexacao());
		}
	}
	
	/**
	 * Verifica se o intressado dos protocolos são os mesmos ou se o cointeressado do anexo é o interessado do principal.
	 *  */
	public void setMensagemInteressadoProtocolos() {
		
		ProtocoloDto principal = anexacaoDto.getProtocoloPrincipal();
		ProtocoloDto anexo = anexacaoDto.getProtocoloDaJuntada();
		
		if( !principal.getInteressado().getId().equals(anexo.getInteressado().getId()) ) {
			
			if( !interessadoProtocoloPrincipalIsCoInteressadoNoProtocoloAnexo() ) {
				anexacaoDto.setPossuiErrosNaJuntada(true);
				anexacaoDto.getMensagensJuntada().add(messages.interessadosAnexacao());
			}
		}
	}
	
	/**
	 * Verifica se a transação é de anexação de acordo com os tipos dos protocolos envolvidos.
	 * Tipos de protocolos que autuam processos não podem ser anexados entre si.
	 *  */
	public void setMensagemTransacaoAnexo() {
		
		TipoProtocoloEnum tipoProtocoloPrincipal = anexacaoDto.getProtocoloPrincipal().getTipoProtocolo();
		TipoProtocoloEnum tipoProtocoloAnexo = anexacaoDto.getProtocoloDaJuntada().getTipoProtocolo();
		
		if( !aTransacaoEhDeAnexacao() ) {
			
			anexacaoDto.setPossuiErrosNaJuntada(true);
			anexacaoDto.getMensagensJuntada().add(messages.transacaoDeAnexo(tipoProtocoloPrincipal, tipoProtocoloAnexo));
		}
	}
	
	/**
	 * Valida se o protocolo a ser anexado está em ordem perante aos demais 
	 * protocolos vinculados ao processo principal.
	 * A rigor um protocolo não poderá ser anexado se existe um com data anterior que ainda não foi juntado ao processo.
	 * */
	public void setMensagemOrdemCronologicaAnexacao() {
		
		List<ProtocoloDto> listProtocolosDoProcessoPrincipal = protocoloConverter.toListDto(protocoloDao.buscaProtocolosNaoJuntadosAoProcessoPrincipal(anexacaoDto.getProtocoloPrincipal()));
		if( !anexoEstaEmOrdemCronologicaDentroDoProcesso(listProtocolosDoProcessoPrincipal)) {

			anexacaoDto.setPossuiErrosNaJuntada(true);
			anexacaoDto.getMensagensJuntada().add(messages.anexoForaOrdemCronologica());
		}
	}
	
	/**
	 * Retorna true caso um dos protocolos seja abertura de tomo, quando um tomo é aberto para 
	 * o protocolo, ele deverá ser o principal da anexação e não mais o protocolo que gerou o processo
	 * @return true /false
	 *  */
	public boolean assuntoProtocolosEhAberturaTomo() {
		
		if( validateGenericJuntada.assuntoProtocoloEhAberturaTomo(anexacaoDto.getProtocoloPrincipal()) || 
			validateGenericJuntada.assuntoProtocoloEhAberturaTomo(anexacaoDto.getProtocoloDaJuntada()) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Retorna false se o protocolo a ser anexado não estiver vinculado ao principal, ou seja,
	 * se o numero do principal não for o mesmo numero de processo do anexo.
	 * Estão fora da regra protocolos de abertura de tomo e protocolos antigos cuja na época
	 * processo inicial não tinha o mesmo número de protocolo.
	 * @return true /false
	 * */
	public boolean anexoEstaVinculadoAoPrincipal() {

		ProtocoloDto principal = anexacaoDto.getProtocoloPrincipal();
		ProtocoloDto anexo = anexacaoDto.getProtocoloDaJuntada();

		return principal.getNumeroProtocolo() != anexo.getNumeroProcesso() 
			   && !principal.getDataEmissao().before(getDataCorteNasRegrasParaProtocoloAntigo())
			   && !assuntoProtocolosEhAberturaTomo() ? false : true;
	}
	
	/**
	 * Retorna true caso o protocolo possa receber anexo de acordo com 
	 * o seu tipo.
	 * 
	 * Tipo zero no protocolo principal (caracteriza processo de leigo) por via de regra não pode receber anexo, somente apenso.
	 * A exceção desta regra precisou ser tratada pois em casos antigos, não havia o conceito de "tipo" na lei de formação
	 * do número de protocolo e eventualmente um protocolo poderá ser do tipo "0" e não ser um processo de leigo
	 * como no exemplo do protocolo 1989009145 que é da empresa 1981200581. 
	 * Passamos a considerar a pedido da Juraciara em 10/2017 anexo para protocolo do tipo zero somente se atender ao caso
	 * exemplificado.
	 * @return true / false
	 * */
	public boolean protocoloPodeReceberAnexo() {
		ProtocoloDto principal = anexacaoDto.getProtocoloPrincipal(); 
		
		if( principal.getTipoProtocolo().getDigito().equals(new Long(0)) && 
			principal.getDataEmissao().before(getDataCorteNasRegrasParaProtocoloAntigo()) &&
			principal.getInteressado().getId().equals(principal.getNumeroProcesso()) ) {
			
			return true;
			
		} else {
			
			switch (principal.getTipoProtocolo()) {
			case LEIGO:
				return false;
			default:
				return true;
			} 
		}
	}
	
	/**
	 * Retorna false se ambos protocolos forem dos tipos 1 - 9, excluindo o tipo sete.
	 * Para estes casos a juntada deverá ser por apensação.
	 * @return true / false
	 * */
	public boolean aTransacaoEhDeAnexacao() {
		
		TipoProtocoloEnum tipoProtocoloPrincipal = anexacaoDto.getProtocoloPrincipal().getTipoProtocolo();
		TipoProtocoloEnum tipoProtocoloAnexo = anexacaoDto.getProtocoloDaJuntada().getTipoProtocolo();
		
		List<Long> listaTiposProtocolo = new ArrayList<Long>();
		for ( Long tipoProtocolo = new Long(1); tipoProtocolo <= 9; tipoProtocolo++ ) {
			
			if( tipoProtocolo != 7) {
				listaTiposProtocolo.add(tipoProtocolo);
			}
		}
		
		if(listaTiposProtocolo.contains(tipoProtocoloPrincipal.getDigito()) && listaTiposProtocolo.contains(tipoProtocoloAnexo)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Retorna true se o interessado do protocolo principal estiver presente como coInteressado
	 * do protocolo a ser anexado
	 * @return true / false
	 * */
	public boolean interessadoProtocoloPrincipalIsCoInteressadoNoProtocoloAnexo() {
		boolean interessadoPrincipalIsCointeressadoAnexo = false;

		PessoaDto interessadoProtocoloPrincipal = anexacaoDto.getProtocoloPrincipal().getInteressado();
		
		Long numeroAnexo = anexacaoDto.getProtocoloDaJuntada().getNumeroProtocolo();
		List<IInteressado> cointeressadosProtocoloAnexo = protocoloDao.getListCoInteressadoPor(numeroAnexo);
		
		for (IInteressado cointeressado : cointeressadosProtocoloAnexo) {
			
			if( interessadoProtocoloPrincipal.getId().equals(cointeressado.getId()) ) {
				
				interessadoPrincipalIsCointeressadoAnexo = true;
			}
		}
		
		return interessadoPrincipalIsCointeressadoAnexo;
	}
	
	/**
	 * Retorna true se o protocolo a ser anexado estiver na ordem de juntada mediante aos
	 * outros protocolos vinculados ao processo principal de acordo com a data que foram cadastrados.
	 * @return true / false
	 * */
	public boolean anexoEstaEmOrdemCronologicaDentroDoProcesso(List<ProtocoloDto> listProtocolosDoProcessoPrincipal) {
		
		listProtocolosDoProcessoPrincipal.sort( (p1, p2) -> p1.getDataEmissao().compareTo(p2.getDataEmissao()) );
		for ( ProtocoloDto protocoloVinculado : listProtocolosDoProcessoPrincipal ) {
			
			return protocoloVinculado.getNumeroProtocoloPaiAnexo() == null 
					&& protocoloVinculado.getNumeroProtocolo().equals(anexacaoDto.getProtocoloDaJuntada().getNumeroProtocolo()) ? true : false; 
		}
		
		return false;
	}
	
	/**
	 * Retorna a data de corte para deixar passar anexação quando o numero protocolo principal é 
	 * diferente do processo. Esta regra foi definida pelo CDOC na pessoa da Juraciara Reis.
	 * Por via de regra a anexação não permite protocolo principal com numero diferente de processo, pois
	 * isso configura que o protocolo não é o inicial, contudo, houve um tempo em que esta regra não valia
	 * 
	 * A validação abaixo é exclusivamente para atender anexação de protocolos antigos. 
	 * @return data
	 * */
	public Date getDataCorteNasRegrasParaProtocoloAntigo() {
		return DateUtils.convertStringToDate("01/01/2012", DateUtils.DD_MM_YYYY);
	}
	
	/** 
	 *  Retorna o objeto anexacaoDto contendo as mensagens de validação 
	 *  sobre o protocolo principal e o protocolo a ser anexado
	 *  @return anexacaoDto
	 */
	public JuntadaProtocoloDto build() {
		return anexacaoDto;
	}

}
