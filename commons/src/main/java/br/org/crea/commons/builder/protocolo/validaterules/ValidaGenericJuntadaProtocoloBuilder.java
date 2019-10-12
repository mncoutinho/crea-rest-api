package br.org.crea.commons.builder.protocolo.validaterules;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.protocolo.EprocessoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.commons.Eprocesso;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;

public class ValidaGenericJuntadaProtocoloBuilder {
	
	
	@Inject ProtocoloDao protocoloDao;

	@Inject EprocessoDao eprocessoDao;
	
	@Inject FuncionarioDao funcionarioDao;
	
	@Inject ProtocoloConverter protocoloConverter;
	
	@Inject DocflowService docflowService;
	
	@Inject DocflowGenericDto genericDocflowDto;

	@Inject HelperMessages messages;
	
	
	/**
	 * Verifica se o protocolo principal ou o protocolo a ser vinculado / desvinculado já foi anexado a outro.
	 * @param dto
	 * */
	public void setMensagemProtocoloJaAnexado (JuntadaProtocoloDto dto) {
		String mensagem = "";
		
		if( dto.getProtocoloPrincipal().estaAnexado() ) {
			
			dto.setPossuiErrosNaJuntada(true);
			mensagem = messages.protocoloPrincipalJaAnexado(dto.getProtocoloPrincipal().getNumeroProtocolo(), dto.getProtocoloPrincipal().getNumeroProtocoloPaiAnexo());
			dto.getMensagensJuntada().add(mensagem);
		}
		
		if( dto.getProtocoloDaJuntada().estaAnexado() ) {
			
			dto.setPossuiErrosNaJuntada(true);
			mensagem = messages.protocoloJaAnexado(dto.getProtocoloDaJuntada().getNumeroProtocolo(), dto.getProtocoloDaJuntada().getNumeroProtocoloPaiAnexo(), dto.getTipoJuntadaProtocolo());
			dto.getMensagensJuntada().add(mensagem);
		}
		
	}
	
	/**
	 * Verifica se o protocolo principal ou o protocolo a ser vinculado / desvinculado já foi apensado a outro.
	 * @param dto
	 * */
	public void setMensagemProtocoloJaApensado(JuntadaProtocoloDto dto) {
		String mensagem = "";
		
		if( dto.getProtocoloPrincipal().estaApensado() ) {
			
			dto.setPossuiErrosNaJuntada(true);
			mensagem = messages.protocoloPrincipalJaApensado(dto.getProtocoloPrincipal().getNumeroProtocolo(), dto.getProtocoloPrincipal().getNumeroProtocoloPaiApenso());
			dto.getMensagensJuntada().add(mensagem);
		}
		
		if( dto.getProtocoloDaJuntada().estaApensado() ) {
			
			dto.setPossuiErrosNaJuntada(true);
			mensagem = messages.protocoloJaApensado(dto.getProtocoloDaJuntada().getNumeroProtocolo(), dto.getProtocoloDaJuntada().getNumeroProtocoloPaiApenso(), dto.getTipoJuntadaProtocolo());
			dto.getMensagensJuntada().add(mensagem);
		}
	}
	
	/**
	 * Verifica se o protocolo principal ou o protocolo a ser vinculado / desvinculado está marcado para inventario
	 * @param dto
	 * */
	public void setMensagemProtocoloMarcadoInventario(JuntadaProtocoloDto dto) {

		if ( protocoloDao.protocoloEstaMarcadoParaInventario(dto.getProtocoloPrincipal().getNumeroProtocolo()) ) {

			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.protocoloInventariado(dto.getProtocoloPrincipal().getNumeroProtocolo()));
		}
		
		if ( protocoloDao.protocoloEstaMarcadoParaInventario(dto.getProtocoloDaJuntada().getNumeroProtocolo()) ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.protocoloInventariado(dto.getProtocoloDaJuntada().getNumeroProtocolo()));
		}
	}
	
	/**
	 * Verifica se funcionario responsável pela ação possui permissão no departamento dos protocolos.
	 * @param dto
	 * */
	public void setMensagemPermissaoUnidadeProtocolo(JuntadaProtocoloDto dto) {
		
		if ( !funcionarioDao.funcionarioPossuiPermissaoTramiteNaUnidade(pesquisaUnidadeProtocolo(dto.getFuncionarioDaJuntada().getId(), dto.getProtocoloPrincipal())) ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.permissaoFuncionarioDepartamento(dto.getProtocoloPrincipal().getNumeroProtocolo()));
		}
		
		if ( !funcionarioDao.funcionarioPossuiPermissaoTramiteNaUnidade(pesquisaUnidadeProtocolo(dto.getFuncionarioDaJuntada().getId(), dto.getProtocoloDaJuntada())) ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.permissaoFuncionarioDepartamento(dto.getProtocoloDaJuntada().getNumeroProtocolo()));
		}
	}
	
	/**
	 * Verifica se o protocolo principal ou o protocolo a ser vinculado / desvinculado está recebido.
	 * @param dto
	 * */
	public void setMensagemProtocoloNaoRecebido(JuntadaProtocoloDto dto) {
		
		if( dto.getProtocoloPrincipal().getUltimoMovimento().getDataRecebimento() == null ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.protocoloNaoRecebido(dto.getProtocoloPrincipal().getNumeroProtocolo()));
		}
		
		if( dto.getProtocoloDaJuntada().getUltimoMovimento().getDataRecebimento() == null ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.protocoloNaoRecebido(dto.getProtocoloDaJuntada().getNumeroProtocolo()));
		}
	}
	
	/**
	 * Verifica se protocolo principal e protocolo da juntada são do mesmo departamento
	 * @param dto
	 * */
	public void setMensagemProtocoloDepartamentoDiferente(JuntadaProtocoloDto dto) {
		String mensagem = "";
		
		Long destinoProtocoloPrincipal = dto.getProtocoloPrincipal().getUltimoMovimento().getIdDepartamentoDestino();
		Long destinoProtocoloDaJuntada = dto.getProtocoloDaJuntada().getUltimoMovimento().getIdDepartamentoDestino();
		
		if( !destinoProtocoloPrincipal.equals(destinoProtocoloDaJuntada) ) {
			
			dto.setPossuiErrosNaJuntada(true);
			mensagem = messages.protocoloDepartamentoDiferente(dto.getProtocoloPrincipal().getNumeroProtocolo(), dto.getProtocoloDaJuntada().getNumeroProtocolo());
			dto.getMensagensJuntada().add(mensagem);
		}
	}
	
	/**
	 * Verifica se protocolo principal e protocolo da juntada estão digitalizados
	 * A regra não permite que protocolo físico seja juntada a protocolo digital ou vice e versa.
	 * @param dto
	 * */
	public void setMensagemProtocoloDigitalizado(JuntadaProtocoloDto dto) {
		
		if( dto.getProtocoloPrincipal().isDigital() != dto.getProtocoloDaJuntada().isDigital()) {
	
			dto.setPossuiErrosNaJuntada(true);
			if( dto.getProtocoloPrincipal().isDigital() ) {
				
				dto.getMensagensJuntada().add( messages.protocoloPrincipalDigitalizado(dto.getTipoJuntadaProtocolo()));
				
			} else {
				
				dto.getMensagensJuntada().add( messages.protocoloDaJuntadaDigitalizado(dto.getTipoJuntadaProtocolo()));
			}
		}
	}
	
	/**
	 * Verificar se o funcionário da juntada está na mesma unidade dos protocolos envolvidos.
	 * @param dto
	 * */
	public void setMensagemFuncionarioJuntadaProtocoloDigital(JuntadaProtocoloDto dto) {
		
		genericDocflowDto.setMatricula(String.valueOf(dto.getFuncionarioDaJuntada().getMatricula()));
		genericDocflowDto.setNumeroProtocolo(dto.getProtocoloPrincipal().getNumeroProtocolo().toString());
		boolean usuarioEstaNaMesmaUnidadeDoProtocoloPrincipal = docflowService.usuarioEstaNaMesmaUnidadeProtocoloTramitado(genericDocflowDto);

		if ( !usuarioEstaNaMesmaUnidadeDoProtocoloPrincipal ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.alocacaoFuncionarioDiferenteProtocoloPrincipal(dto));
		}
		
		genericDocflowDto.setNumeroProtocolo(dto.getProtocoloDaJuntada().getNumeroProtocolo().toString());
		boolean usuarioEstaNaMesmaUnidadeDoProtocoloAnexo = docflowService.usuarioEstaNaMesmaUnidadeProtocoloTramitado(genericDocflowDto);

		if( !usuarioEstaNaMesmaUnidadeDoProtocoloAnexo ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.alocacaoFuncionarioDiferenteProtocoloDaJuntada(dto));
		}
	}
	
	/**
	 * Verificar se os protocolos da juntada estão na mesma unidade nos sistemas Corporativo e Docflow.
	 * @param dto
	 * */
	public void setMensagemLocalizacaoEprocesso(JuntadaProtocoloDto dto) {

		genericDocflowDto.setMatricula(String.valueOf(dto.getFuncionarioDaJuntada().getMatricula()));
		ProtocoloDto protocoloPrincipal = dto.getProtocoloPrincipal();
		genericDocflowDto.setNumeroProtocolo(protocoloPrincipal.getNumeroProtocolo().toString());
		boolean movimentoProtocoloPrincipalEstaDivergente = docflowService.movimentoProtocoloPossuiDivergencia(genericDocflowDto, protocoloPrincipal.getUltimoMovimento().getIdDepartamentoDestino());

		if ( movimentoProtocoloPrincipalEstaDivergente ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.localizacaoEprocesso(protocoloPrincipal.getNumeroProtocolo()));
		}
		
		ProtocoloDto protocoloAnexo = dto.getProtocoloDaJuntada();
		genericDocflowDto.setNumeroProtocolo(protocoloAnexo.getNumeroProtocolo().toString());
		boolean movimentoProtocoloAnexoEstaDivergente = docflowService.movimentoProtocoloPossuiDivergencia(genericDocflowDto, protocoloAnexo.getUltimoMovimento().getIdDepartamentoDestino());

		if( movimentoProtocoloAnexoEstaDivergente ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.localizacaoEprocesso(protocoloAnexo.getNumeroProtocolo()));
		}
	}
	
	/**
	 * Verifica se o status da transação dos protocolos está com divergência, ou seja, 
	 * se houve uma tentativa de operar com o protocolo e a ação não foi concretizada.
	 * Ex. pendente tramitação, recebimento, anexação ou demais ações do protocolo.
	 * @param dto
	 * */
	public void setMensagemStatusDivergente(JuntadaProtocoloDto dto) {
		
		if( dto.getProtocoloPrincipal().statusTransacaoEstaDivergente() ) {
			
			dto.setPossuiErrosNaJuntada(true);
			String status = dto.getProtocoloPrincipal().getStatusTransacao();
			dto.getMensagensJuntada().add(messages.protocoloPrincipalStatusTransacao(dto.getProtocoloPrincipal().getNumeroProtocolo(), status));
		}
		
		if( dto.getProtocoloDaJuntada().statusTransacaoEstaDivergente() ) {
			
			dto.setPossuiErrosNaJuntada(true);
			String status = dto.getProtocoloDaJuntada().getStatusTransacao();
			dto.getMensagensJuntada().add(messages.protocoloJuntadaStatusTransacao(dto.getProtocoloDaJuntada().getNumeroProtocolo(), status));
		}
	}
	
	/**
	 * Verifica se o protocolo a ser desvinculado estava juntado ao princiṕal
	 * Se for o caso, não será permitido desanexar.
	 * */
	public void setMensagemProtocoloASerDesvinculado(JuntadaProtocoloDto dto) {
		
		Long protocoloADesvincular = dto.getProtocoloDaJuntada().getNumeroProtocolo();
		Long protocoloPrincipal = dto.getProtocoloPrincipal().getNumeroProtocolo();
		
		if( !protocoloDao.protocoloEstaJuntadoAoProtocoloPrincipal(protocoloPrincipal, protocoloADesvincular)) {
		
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.desvincularProtocoloJuntada(protocoloPrincipal, protocoloADesvincular));
		}
	}
	
	/**
	 * Seta as informações do usuário da juntada para o objeto que será retornado.
	 * @param usuario
	 * @param dto
	 * */
	public void setFuncionarioDaJuntada(UserFrontDto usuario, JuntadaProtocoloDto dto) {
		
		FuncionarioDto funcionarioDaJuntada = new FuncionarioDto();
		funcionarioDaJuntada.setId(usuario.getIdFuncionario());
		funcionarioDaJuntada.setIdPessoa(usuario.getIdPessoa());
		funcionarioDaJuntada.setMatricula(usuario.getMatricula());
		funcionarioDaJuntada.setIdDepartamento(Long.parseLong(usuario.getDepartamento().getId()));
		dto.setFuncionarioDaJuntada(funcionarioDaJuntada);
	}
	
	/**
	 * Verifica se o protocolo informado para ser juntado não é o próprio principal
	 * @param dto
	 * */
	public void setMensagemIdentidadeProtocoloJuntada(JuntadaProtocoloDto dto) {
		
		if( dto.getProtocoloPrincipal().getNumeroProtocolo().equals(dto.getProtocoloDaJuntada().getNumeroProtocolo()) ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.protocoloJuntadaIgualAoPrincipal());
		}
	}
	
	/**
	 * Valida o estado dos protocolos: se anexado, apensado, recebido, inventariado, digitalizado,
	 * se os protocolos informados são os mesmos (apenso igual ao principal) para permitir a juntada entreos protocolos
	 * @param dto
	 * */
	public void validarEstadoProtocolosParaJuntada(JuntadaProtocoloDto dto) {
		
		setMensagemIdentidadeProtocoloJuntada(dto);
		setMensagemStatusDivergente(dto);
		setMensagemProtocoloMarcadoInventario(dto);
		setMensagemProtocoloJaAnexado(dto);
		setMensagemProtocoloJaApensado(dto);
		setMensagemProtocoloNaoRecebido(dto);
		setMensagemProtocoloDigitalizado(dto);
		
	}
	
	/**
	 * Valida a localização dos protocolos e permissão do funcionário dentro da unidade
	 * para efetuar a juntada.
	 * @param dto
	 * */
	public void validarDepartamentoProtocolos(JuntadaProtocoloDto dto) {
		
		setMensagemProtocoloDepartamentoDiferente(dto);
		setMensagemPermissaoUnidadeProtocolo(dto);
	}
	
	/**
	 * Verifica se o serviço de anexacao está liberado no Docfow
	 * @param dto
	 * */
	public void validarDesbloqueioEprocesso(JuntadaProtocoloDto dto) {
		
		Eprocesso eprocesso = eprocessoDao.getBy(1L);
		
		if( !eprocesso.estaLiberado() ) {
			
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.eprocessoBloqueado());
		}
		
		switch (dto.getTipoJuntadaProtocolo()) {
		case ANEXACAO:
			bloqueioAnexar(dto, eprocesso); 
			break;
		case DESANEXACAO:
			bloqueioDesanexar(dto, eprocesso); 
		case APENSACAO:
			bloqueioApensar(dto, eprocesso); 	
			break;
		case DESAPENSACAO:
			bloqeioDesapensar(dto, eprocesso); 	
		break;	
		default:break;
		}
	}
	
	/**
	 * Flag de controle para verificar se ação de anexar está liberada no Docflow
	 * @param dto
	 * @param eprocesso
	 * */
	private void bloqueioAnexar(JuntadaProtocoloDto dto, Eprocesso eprocesso) {
		if( !eprocesso.podeAnexar() ) {
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.eprocessoBloqueadoParaAnexar()); 
		}
	}

	/**
	 * Flag de controle para verificar se ação de desanexar está liberada no Docflow
	 * @param dto
	 * @param eprocesso
	 * */
	private void bloqueioDesanexar(JuntadaProtocoloDto dto, Eprocesso eprocesso) {
		if( !eprocesso.podeDesanexar() ) {
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.eprocessoBloqueadoParaDesanexar()); 
		}
	}

	/**
	 * Flag de controle para verificar se ação de apensar está liberada no Docflow
	 * @param dto
	 * @param eprocesso
	 * */
	private void bloqueioApensar(JuntadaProtocoloDto dto, Eprocesso eprocesso) {
		if( !eprocesso.podeApensar() ) {
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.eprocessoBloqueadoParaApensar()); 
		}
	}

	/**
	 * Flag de controle para verificar se ação de desapensar está liberada no Docflow
	 * @param dto
	 * @param eprocesso
	 * */
	private void bloqeioDesapensar(JuntadaProtocoloDto dto, Eprocesso eprocesso) {
		
		if( !eprocesso.podeDesapensar() ) {
			dto.setPossuiErrosNaJuntada(true);
			dto.getMensagensJuntada().add(messages.eprocessoBloqueadoParaDesapensar()); 
		}
	}
	
	/**
	 * Popula dados para pesquisar permissão do usuário nas unidades dos protocolos envolvidos na juntada.
	 * @param idFuncionario, protocoloDto
	 * @return pesquisa
	 * */
	public PesquisaGenericDto pesquisaUnidadeProtocolo(Long idFuncionario, ProtocoloDto protocoloDto) {
		
		PesquisaGenericDto pesquisa = new PesquisaGenericDto();
		pesquisa.setIdDepartamento(protocoloDto.getUltimoMovimento().getIdDepartamentoDestino());
		pesquisa.setIdFuncionario(idFuncionario);

		return pesquisa;
	}
	
	/**
	 * Verifica se os protocolos da juntada são do mesmo processo
	 * @param dto
	 * @return true/false
	 * */
	public boolean protocolosSaoDoMesmoProcesso(JuntadaProtocoloDto dto) {
		return dto.getProtocoloPrincipal().getNumeroProcesso().equals( dto.getProtocoloDaJuntada().getNumeroProcesso() ) ? true : false;
	}
	
	/**
	 * Verifica se o assunto do protocolo da juntada é abertura de tomo
	 * Quando verdadeiro, a juntada pode acontecer mesmo que protocolos sejam de processos diferentes
	 * @param dto
	 * @return true/false
	 * */
	public boolean assuntoProtocoloEhAberturaTomo(ProtocoloDto dto) {
		
		if ( dto.getAssunto().getDescricao().contains("ABERTURA DE TOMO") || 
			 dto.getAssunto().getId().equals(new Long(1239))  || 
			 dto.getAssunto().getId().equals(new Long(10205)) ||
			 dto.getAssunto().getId().equals(new Long(10205))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Retorna true se o tipo de juntada foi informada no consumo do serviço
	 * Ex. ANEXACAO, APENSACAO
	 * @return true / false
	 * */
	public boolean tipoJuntadaFoiInformada(JuntadaProtocoloDto dto ) {
		String mensagem = "";
		if( dto.getTipoJuntadaProtocolo() == null ) {
			
			dto.setPossuiErrosNaJuntada(true);
			mensagem = messages.tipoJuntadaNaoInformada();
			dto.getMensagensJuntada().add(mensagem);			
		}
		
		return mensagem.isEmpty() ? true : false;

	}
	
	/**
	 * Retorna true se o módulo que está consumindo o serviço foi informado
	 * Ex. SIACOL, CORPORATIVO etc
	 * @return true / false
	 * */
	public boolean moduloSistemaFoiInformado(JuntadaProtocoloDto dto ) {
		String mensagem = "";
		if( dto.getModulo() == null ) {
			
			dto.setPossuiErrosNaJuntada(true);
			mensagem = messages.moduloSistemaNaoInformado();
			dto.getMensagensJuntada().add(mensagem);			
		}
		
		return mensagem.isEmpty() ? true : false;
	}
}
