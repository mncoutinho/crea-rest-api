package br.org.crea.protocolo.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import br.org.crea.commons.builder.protocolo.ImportarProtocoloSiacolBuilder;
import br.org.crea.commons.builder.protocolo.validaterules.ValidaRecebimentoProtocoloBuilder;
import br.org.crea.commons.converter.cadastro.funcionario.FuncionarioConverter;
import br.org.crea.commons.dao.cadastro.ProtocoloDemandaDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.protocolo.MovimentoDao;
import br.org.crea.commons.dao.protocolo.ObservacaoMovimentoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.dao.siacol.HabilidadePessoaDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.SiacolProtocoloHistoricoSaidaDao;
import br.org.crea.commons.docflow.converter.DocflowGenericConverter;
import br.org.crea.commons.docflow.model.response.ResponseTramiteProtocoloDocflow;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.factory.AuditaProtocoloFactory;
import br.org.crea.commons.factory.siacol.AuditaSiacolProtocoloFactory;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.models.protocolo.dtos.TramitacaoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.SiacolProtocoloHistoricoSaida;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.protocolo.ProtocoloService;
import br.org.crea.commons.util.EmailUtil;
import br.org.crea.commons.util.StringUtil;

public class EnviarProtocoloBuilder {
	
	@Inject ProtocoloDemandaDao protocoloDemandaDao;
	
	@Inject DocflowService docflowService;

	@Inject HelperMessages messages;
	@Inject MovimentoDao movimentoDao;

	@Inject DepartamentoDao departamentoDao;

	@Inject ProtocoloDao protocoloDao;
	
	@Inject PessoaDao pessoaDao;

	@Inject ObservacaoMovimentoDao observacaoMovimentoDao;

	@Inject ImportarProtocoloSiacolBuilder importSiacol;

	@Inject HttpClientGoApi httpGoApi;

	@Inject DocflowGenericConverter converterDocflow;

	@Inject EmailService emailService;

	@Inject FuncionarioDao funcionarioDao;
	
	@Inject FuncionarioConverter funcionarioConverter;

	@Inject AuditaProtocoloFactory auditFactory;
		
	@Inject ProtocoloSiacolDao protocoloSiacolDao;
	
	@Inject HabilidadePessoaDao habilidadePessoaDao;
			
	@Inject ProtocoloService protocoloService;
		
	@Inject ValidaRecebimentoProtocoloBuilder validateRecebimento;
		
	@Inject ReceberProtocoloBuilder receberProtocoloBuilder;
	
	@Inject SiacolProtocoloHistoricoSaidaDao siacolProtocoloHistoricoSaidaDao;
	
	@Inject AuditaSiacolProtocoloFactory audita;
	
	@Inject EmailUtil emailUtil;
	
	private int totalErroTransacao;
	
	private int totalSucessoTransacao;

	private List<TramiteDto> listTramiteDto;
	
	private UserFrontDto usuarioDto;
	
	private ModuloSistema moduloSistema;
	
	private TramitacaoProtocoloDto tramitacaoProtocoloDto;
	
	private ExecutorService threadPool;

	/**
	 * Efetua de fato a movimentação dos protocolos validados, gravando as
	 * alterações nos bancos (corporativo e docflow)
	 * 
	 * Para cada protocolo sem erros, define o status como "em tramitação" e
	 * grava a movimentação no docflow, corporativo e nas tabelas do siacol
	 * 
	 * O destinatário recebe por email relação com todos os protocolos tramitados com sucesso.
	 * O remetente por email relação com todos os protocolos não tramitados com erro.
	 * 
	 * @param listDto - contém uma lista de protocolos e suas informações de seu trâmite
	 * @return this - retorna a própria classe que contém a lista de TramiteDto
	 *         validados
	 */
	public EnviarProtocoloBuilder gerarMovimentoProtocolo(ModuloSistema modulo, List<TramiteDto> listDto, UserFrontDto usuario) {
		usuarioDto = usuario;
		moduloSistema = modulo;

		listTramiteDto = listDto;
		if (listTramiteDto.size() > 0) {

			for (TramiteDto dto : listTramiteDto) {

				if (!dto.isPossuiErros()) {

					setStatusProtocolosEmTramitacao(dto.getNumeroProtocolo());
					movimentarProtocoloDocflow(dto).movimentarProtocoloCorporativo(dto, modulo, usuario);

					auditFactory.auditaAcaoProtocolo(dto, usuarioDto, dto.getMensagensDoTramite().get(0), moduloSistema, false);
					
					desabilitarProtocoloSiacol(dto.getNumeroProtocolo());
					if (dto.destinoEhSiacol()) {
						importarParaSiacol(dto, usuario);
					}
				}				
			}
			enviarEmailTramiteSucesso(departamentoDao.buscaDepartamentoPor(listTramiteDto.get(0).getIdDepartamentoDestino()));
			enviarEmailTramiteErro(funcionarioDao.getBy(usuario.getIdFuncionario()));
		}
		return this;
	}

	private void desabilitarProtocoloSiacol(Long numeroProtocolo) {
		ProtocoloSiacol protocoloSiacol = new ProtocoloSiacol();
		protocoloSiacol = protocoloSiacolDao.getProtocoloBy(numeroProtocolo);
		if (protocoloSiacol != null) {
			protocoloSiacol.setAtivo(false);
			protocoloSiacolDao.update(protocoloSiacol);	
		}
	}

	private void importarParaSiacol(TramiteDto dto, UserFrontDto usuario) {	
		ProtocoloSiacol protocoloSiacol = new ProtocoloSiacol();
		protocoloSiacol = protocoloSiacolDao.getProtocoloBy(dto.getNumeroProtocolo());
		if (protocoloSiacol != null) {
			salvaHistoricoSiacol(dto);
		}
			importProtocoloSiacol(dto, usuario);
			verificaProvisorio(dto);
			verificaOficiado(dto, usuario);	
			inserirProtocoloPrimeiraInstancia(dto, usuario);	
	}

	private void salvaHistoricoSiacol(TramiteDto dto) {
		ProtocoloSiacol protocoloSiacol = new ProtocoloSiacol();
		protocoloSiacol = protocoloSiacolDao.getProtocoloBy(dto.getNumeroProtocolo());
		
		SiacolProtocoloHistoricoSaida ProtocoloHistoricoSaida = 
				siacolProtocoloHistoricoSaidaDao.getHistorioBy(protocoloSiacol.getDepartamento().getId(),dto.getNumeroProtocolo()); 
				
		if (protocoloSiacol != null) {
			SiacolProtocoloHistoricoSaida ProtocoloHistorico = ProtocoloHistoricoSaida != null ? ProtocoloHistoricoSaida : new SiacolProtocoloHistoricoSaida();
						
			ProtocoloHistorico.setAdReferendum(protocoloSiacol.getAdReferendum());
			ProtocoloHistorico.setProvisorio(protocoloSiacol.getProvisorio());
			
			ProtocoloHistorico.setProtocoloSiacol(protocoloSiacol);
			ProtocoloHistorico.setNumeroProtocolo(protocoloSiacol.getNumeroProtocolo());
			ProtocoloHistorico.setAssuntoSiacol(protocoloSiacol.getAssuntoSiacol());
			ProtocoloHistorico.setDepartamento(protocoloSiacol.getDepartamento());
//			ProtocoloHistorico.setDecisao();
//			ProtocoloHistorico.setDecisaoAdReferendum();	
			
			ProtocoloHistorico.setStatus(protocoloSiacol.getStatus());
			ProtocoloHistorico.setUltimoAnalista(
					(protocoloSiacol.getIdResponsavel() != null &&  
					!protocoloSiacol.getIdResponsavel().equals(new Long(0))) ?
							protocoloSiacol.getIdResponsavel() : protocoloSiacol.getUltimoAnalista());
			ProtocoloHistorico.setUltimoConselheiro(protocoloSiacol.getConselheiroDevolucao());
			
			if (ProtocoloHistorico.getStatus() == StatusProtocoloSiacol.PROVISORIO_ASSINADO) {
				ProtocoloHistorico.setStatus(StatusProtocoloSiacol.A_PAUTAR_PROVISORIO);
			}
			if (protocoloSiacol.temEvento()) {
				ProtocoloHistorico.setEvento(protocoloSiacol.getEvento());
			}
			
			if (ProtocoloHistorico.getId() == null ) {
				siacolProtocoloHistoricoSaidaDao.create(ProtocoloHistorico);
			} else {
				siacolProtocoloHistoricoSaidaDao.update(ProtocoloHistorico);
			}
								
			SiacolProtocoloHistoricoSaida ProtocoloHistoricoEntrada = siacolProtocoloHistoricoSaidaDao.getHistorioBy(dto.getIdDepartamentoDestino(),dto.getNumeroProtocolo()); 
				protocoloSiacol.setStatus(ProtocoloHistoricoEntrada != null ? 
						ProtocoloHistoricoEntrada.getStatus() : StatusProtocoloSiacol.AGUARDANDO_RECEBIMENTO);		
				protocoloSiacol.setAdReferendum(ProtocoloHistoricoEntrada != null ? 
						ProtocoloHistoricoEntrada.isAdReferendum() :  false);				
				protocoloSiacol.setProvisorio(ProtocoloHistoricoEntrada != null ? 
						ProtocoloHistoricoEntrada.isProvisorio() : false);				
				protocoloSiacol.setAssuntoSiacol(ProtocoloHistoricoEntrada != null ? 
						ProtocoloHistoricoEntrada.getAssuntoSiacol() : null);				
				protocoloSiacol.setUltimoAnalista(ProtocoloHistoricoEntrada != null ? 
						ProtocoloHistoricoEntrada.getUltimoAnalista() : null);				
				protocoloSiacol.setConselheiroDevolucao(ProtocoloHistoricoEntrada != null ? 
						ProtocoloHistoricoEntrada.getUltimoConselheiro() : null);	
				protocoloSiacol.setEvento(ProtocoloHistoricoEntrada != null ? 
						ProtocoloHistoricoEntrada.getEvento() : null);	
				
				if (ProtocoloHistoricoEntrada != null) {
					Long idResponsavel = habilidadePessoaDao.verificaAtivoDepartamento(ProtocoloHistoricoEntrada.getUltimoAnalista(), ProtocoloHistoricoEntrada.getDepartamento().getId())
							? ProtocoloHistoricoEntrada.getUltimoAnalista()
							: departamentoDao.getBy(new Long(230201)).getCoordenador().getId();
							
					protocoloSiacol.setIdResponsavel(idResponsavel);
					protocoloSiacol.setNomeResponsavel(protocoloSiacol.getIdResponsavel() != null ? 
							pessoaDao.getBy(protocoloSiacol.getIdResponsavel()).getNome() : null);
				}else {
					protocoloSiacol.setIdResponsavel(null);
				}
				
				protocoloSiacol.setAtivo(true);
				protocoloSiacol.setUrgenciaVotado(false);
				
				protocoloSiacolDao.update(protocoloSiacol);					
		}
	}
	
	private void verificaProvisorio(TramiteDto dto) {
		ProtocoloSiacol protocoloSiacol = new ProtocoloSiacol();
		protocoloSiacol = protocoloSiacolDao.getProtocoloBy(dto.getNumeroProtocolo());
		if (protocoloSiacol != null && protocoloSiacol.getStatus().getTipo() == "A_PAUTAR_PROVISORIO" ) {
			receberProtocoloProvisorio(protocoloSiacol, dto);
		}
	}

	private void verificaOficiado(TramiteDto dto, UserFrontDto usuario) {
		Long idProtocoloOficiado = protocoloDemandaDao.getNumeroProtocoloExigencia(dto.getNumeroProtocolo());
		if (idProtocoloOficiado != null) {	
					
			ProtocoloSiacol protocoloSiacolOficiado = protocoloSiacolDao.getProtocoloBy(idProtocoloOficiado);
			protocoloSiacolOficiado.setIdResponsavel(protocoloSiacolOficiado.getUltimoAnalista());
			protocoloSiacolOficiado.setNomeResponsavel(pessoaDao.getBy(protocoloSiacolOficiado.getUltimoAnalista()).getNome());
			protocoloSiacolOficiado.setStatus(StatusProtocoloSiacol.ANALISE_CUMPRIMENTO_OFICIO);
			audita.alteraStatus(null, protocoloSiacolOficiado, usuario);
			protocoloSiacolDao.update(protocoloSiacolOficiado);
			
			ProtocoloSiacol protocoloSiacolFilho = protocoloSiacolDao.getProtocoloBy(dto.getNumeroProtocolo());
			protocoloSiacolFilho.setIdResponsavel(protocoloSiacolOficiado.getUltimoAnalista());
			protocoloSiacolFilho.setNomeResponsavel(pessoaDao.getBy(protocoloSiacolOficiado.getUltimoAnalista()).getNome());
			protocoloSiacolDao.update(protocoloSiacolFilho);
			
		}
	}
	
	
	private void inserirProtocoloPrimeiraInstancia(TramiteDto dto, UserFrontDto usuario) {
		
		Long idProtocoloPrimeiraInstancia = protocoloDemandaDao.getIdProtocoloRecurso(dto.getNumeroProtocolo());
		if (idProtocoloPrimeiraInstancia != null) {	
			ProtocoloSiacol protocoloPrimeiraInstancia = protocoloSiacolDao.getProtocoloBy(idProtocoloPrimeiraInstancia);
			ProtocoloSiacol protocoloSiacol = protocoloSiacolDao.getProtocoloBy(dto.getNumeroProtocolo());
			protocoloSiacol.setProtocoloPrimeiraInstancia(protocoloPrimeiraInstancia);
			protocoloSiacolDao.update(protocoloSiacol);	
		}
	}

	private void receberProtocoloProvisorio(ProtocoloSiacol protocoloSiacol, TramiteDto dto) {
		try {
			
			Funcionario funcionario = new Funcionario();
			funcionario = funcionarioDao.getFuncionarioBy(new Long(9001));
					
			List<TramiteDto> listProtocoloTramite = new ArrayList<TramiteDto>();
			listProtocoloTramite.add(dto);
			
			Departamento departamento = new Departamento();
			departamento = departamentoDao.getBy(dto.getIdDepartamentoDestino());
			
			UserFrontDto userFrontDto = new UserFrontDto();
			userFrontDto.setIdFuncionario(funcionario.getId());
			userFrontDto.setIdPessoa(funcionario.getIdPessoa());
			
			TramitacaoProtocoloDto tramitacaoProtocoloDto = new TramitacaoProtocoloDto();
			tramitacaoProtocoloDto.setFuncionarioTramitacao(funcionarioConverter.toDto(funcionario));
			tramitacaoProtocoloDto.setIdDepartamentoDestino(departamento.getId());
			tramitacaoProtocoloDto.setIdDepartamentoPaiDestino(departamento.getDepartamentoPai().getId());
			tramitacaoProtocoloDto.setListProtocolos(listProtocoloTramite);
			tramitacaoProtocoloDto.setModulo(ModuloSistema.SIACOL);	
		
			receber(tramitacaoProtocoloDto, userFrontDto);
			
			
		} catch (Throwable e) {
		}
		
	}

	public List<TramiteDto> receber(final TramitacaoProtocoloDto dto, final UserFrontDto usuario) throws InterruptedException, ExecutionException {
		
		tramitacaoProtocoloDto = dto;
		
		this.threadPool = Executors.newCachedThreadPool();
		Future<List<TramiteDto>> resultTramite = null;
		
		try {
			resultTramite = threadPool.submit(new Callable<List<TramiteDto>>() {

				@Override
				public List<TramiteDto> call() throws Exception {
					return receberProtocoloBuilder.gerarRecebimentoProtocolo(dto.getModulo(),
							validateRecebimento.validarRecebimento(tramitacaoProtocoloDto, usuario).build(), usuario).buildMovimento();
				}
			});
			
			if(this.threadPool.isTerminated()) {
				this.threadPool.shutdown();
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("TramiteProtocoloService || receber", StringUtil.convertObjectToJson(dto), e);
		}
		return resultTramite.get();
	}

	/**
	 * Movimenta o protocolo no Docflow
	 * 
	 * Se o protocolo é digital e foi validado sem erros, envia informações do
	 * movimento de envio para serem gravadas no docflow e adiciona mensagem de
	 * erro no dto se houver.
	 * 
	 * @param dto
	 * @return this
	 */
	public EnviarProtocoloBuilder movimentarProtocoloDocflow(TramiteDto dto) {
		ResponseTramiteProtocoloDocflow responseDocflow = new ResponseTramiteProtocoloDocflow();

		if (dto.isProtocoloEhDigital() && !dto.isPossuiErros()) {

			responseDocflow = docflowService.enviarProtocolo(converterDocflow.toDocFlowGenericDto(dto));
			setMensagemTramiteDocflow(dto, responseDocflow);
		}

		return this;
	}

	/**
	 * Movimenta o protocolo no Corporativo
	 * 
	 * Se o protocolo não possui erros, executa as seguintes ações: grava a
	 * movimentação do protocolo, define no dto que não possui erros obtém nome
	 * do departamento para adicionar mensagem de confirmação de envio do
	 * protocolo, grava observacao do movimento, define status do protocolo
	 * tramitado como normal, grava a movimentação dos protocolos anexos, grava
	 * a movimentação dos protocolos apensos
	 * 
	 * @param dto
	 * @param modulo 
	 * @param usuario 
	 * @return this
	 */
	public EnviarProtocoloBuilder movimentarProtocoloCorporativo(TramiteDto dto, ModuloSistema modulo, UserFrontDto usuario) {

		if (!dto.isPossuiErros()) {

			Movimento movimento = movimentoDao.gerarMovimentoProtocolo(dto, dto.getNumeroProtocolo());
			dto.setPossuiErros(false);

			Departamento destinoTramite = departamentoDao.getBy(dto.getIdDepartamentoDestino());
			dto.getMensagensDoTramite().add(messages.confirmacaoEnvioProtocolo(dto.getNumeroProtocolo(), destinoTramite.getNome()));

			gravarObservacaoMovimento(dto, movimento);

			movimentoDao.movimentarAnexosDoProtocoloEmTramite(dto, modulo, usuario);
			movimentoDao.movimentarApensosDoProtocoloEmTramite(dto, modulo, usuario);
		}
		
		normalizaStatusProtocolo(dto.getNumeroProtocolo());
		return this;
	}

	/**
	 * Grava as movimentações na tabela do SIACOL
	 * 
	 * Obtém módulo do departamento para verificar se faz parte do módulo
	 * Siacol, e caso faça parte grava nas tabelas do Siacol
	 * 
	 * @param dto
	 * @return this
	 */
	public EnviarProtocoloBuilder importProtocoloSiacol(TramiteDto dto, UserFrontDto usuario) {

		importSiacol.protocolo(dto.getNumeroProtocolo()).origem(dto.getUltimoMovimento()).destino(dto.getIdDepartamentoDestino()).responsavel(usuario).insertOrUpdate(usuario, dto);

		return this;
	}

	/**
	 * Grava no banco o status do protocolo tramitado como "normal"
	 * @param numeroProtocolo
	 */
	public void normalizaStatusProtocolo(Long numeroProtocolo) {

		try {

			Protocolo protocolo = protocoloDao.getProtocoloBy(numeroProtocolo);
			protocolo.setIdStatusTransacao(new Long(0));
			protocoloDao.update(protocolo);

		} catch (Throwable e) {
			httpGoApi.geraLog("EnviarProtocoloBuilder || normalizaStatusProtocolo", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
	}

	/**
	 * Grava a observação do movimento
	 * 
	 * @param dto
	 * @param movimento
	 */
	public void gravarObservacaoMovimento(TramiteDto dto, Movimento movimento) {

		if (dto.getIdObservacaoTramite() != null) {
			observacaoMovimentoDao.gerarObservacaoMovimentoNoTramite(dto, movimento);
		}
	}

	/**
	 * Adiciona mensagem de erro no TramiteDto, se ocorrer falha na gravação dos
	 * dados no Docflow
	 * 
	 * @param dto
	 * @param responseDocflow
	 */
	public void setMensagemTramiteDocflow(TramiteDto dto, ResponseTramiteProtocoloDocflow responseDocflow) {

		if (responseDocflow.hasError()) {

			dto.setPossuiErros(true);
			dto.getMensagensDoTramite().add(messages.erroTramiteEnvioDocflow(dto.getNumeroProtocolo(), responseDocflow.getMessage().getValue()));

			String mensagemErro = responseDocflow.getMessage().getValue();
			auditFactory.auditaAcaoProtocolo(dto, usuarioDto, mensagemErro, moduloSistema, true);
		}
	}

	/**
	 * Grava no banco o status do protocolo como "em tramitação"
	 * @param numeroProtocolo
	 */
	public void setStatusProtocolosEmTramitacao(Long numeroProtocolo) {

		try {

			Protocolo protocolo = protocoloDao.getProtocoloBy(numeroProtocolo);
			protocolo.setIdStatusTransacao(new Long(1));
			protocoloDao.update(protocolo);

		} catch (Throwable e) {
			httpGoApi.geraLog("EnviarProtocoloBuilder || setStatusProtocolosEmTramitacao", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
	}

	/**
	 * Retorna lista de TramiteDto criada nesta classe
	 * @return lista de TramiteDto
	 */
	public List<TramiteDto> buildMovimento() {
		return listTramiteDto;
	}

	/**
	 * Quem envia: "cdoc@crea-rj.org.br" Quem recebe: a coordenação de destino +
	 * (backup) docflow@crea-rj.org.br
	 * @param departamento
	 */
	private void enviarEmailTramiteSucesso(Departamento departamento) {
		try {
			String mensagem = messages.corpoEmailSucessoTramiteEnvio(departamento.getNome(), corpoEmailTramitesEfetuados(), totalSucessoTransacao);
			
			if(totalSucessoTransacao > 0){
			
				EmailEnvioDto email = new EmailEnvioDto();
				email.setMensagem(mensagem);
				email.setAssunto("SIPRO - Chegada de protocolos na unidade " + departamento.getNome());
	
				List<DestinatarioEmailDto> lisDestinatarioEmails = emailUtil.montarListaDestinatarios(departamento.getEmailCoordenacao(), departamento.getNome());
	
				if (!lisDestinatarioEmails.isEmpty()) {
					email.setDestinatarios(lisDestinatarioEmails);
					email.setDestinatariosCC(emailUtil.montarListaDestinatarios("docflow@crea-rj.org.br", "BACKUP"));
				} else {
					email.setDestinatarios(emailUtil.montarListaDestinatarios("docflow@crea-rj.org.br", "BACKUP"));
				}
	
				email.setEmissor("cdoc@crea-rj.org.br");
				email.setDataUltimoEnvio(new Date());
	
				emailService.envia(email);
			}	
		} catch (Exception e) {
			httpGoApi.geraLog("EnviarProtocoloBuilder || enviarEmailTramiteSucesso", StringUtil.convertObjectToJson(departamento), e);
		}
	}

	/**
	 * Quem envia: cdoc@crea-rj.org.br Quem recebe: funcionario da tramitação e
	 * / ou coordenação do funcionário + (backup) docflow@crea-rj.org.br
	 * 
	 * @param mensagens
	 * @param funcionario
	 */
	private void enviarEmailTramiteErro(Funcionario funcionario) {
		try {
					
			String mensagem = messages.corpoEmailErrosTramiteEnvio(funcionario.getNome(), corpoEmailTramitesNaoEfetuados(), totalErroTransacao);
			
			if(totalErroTransacao > 0){
				EmailEnvioDto email = new EmailEnvioDto();
				email.setMensagem(mensagem);
				email.setAssunto("SIPRO - Envio de protocolos com erros");
	
				List<DestinatarioEmailDto> listaDestinatarioEmails = emailUtil.montarListaDestinatarios(funcionario.getPessoaFisica());
	
				if (!listaDestinatarioEmails.isEmpty()) {
					email.setDestinatarios(listaDestinatarioEmails);
					email.setDestinatariosCC(emailUtil.montarListaDestinatarios("docflow@crea-rj.org.br", "BACKUP"));
				} else {
					email.setDestinatarios(emailUtil.montarListaDestinatarios("docflow@crea-rj.org.br", "BACKUP"));
				}
	
				email.setEmissor("cdoc@crea-rj.org.br");
				email.setDataUltimoEnvio(new Date());
	
				emailService.envia(email);
			}	
		} catch (Throwable e) {
			httpGoApi.geraLog("EnviarProtocoloBuilder || enviarEmailTramiteErro", StringUtil.convertObjectToJson(funcionario), e);
		}

	}
	
	/**
	 * Captura cada mensagem por protocolo para montar o texto do email a ser enviado para o remetente.
	 * @return corpoEmail
	 * */
	public String corpoEmailTramitesNaoEfetuados(){
		totalErroTransacao = 0;
		
		StringBuilder corpoEmail = new StringBuilder();
		
		for(TramiteDto tramiteDto : listTramiteDto){
			if(tramiteDto.isPossuiErros()){
				tramiteDto.getMensagensDoTramite().forEach(mensagem-> corpoEmail.append("<li>" + mensagem));
				totalErroTransacao++;
			}	
		}
		
		return corpoEmail.toString();
	}
	
	/**
	 * Captura cada mensagem por protocolo para montar o texto do email a ser enviado para o destinatário.
	 * @return corpoEmail
	 * */
	public String corpoEmailTramitesEfetuados(){
		totalSucessoTransacao = 0;
		StringBuilder corpoEmail = new StringBuilder();
		
		for(TramiteDto tramiteDto : listTramiteDto){
			if(!tramiteDto.isPossuiErros()){
				corpoEmail.append("<li>" + tramiteDto.getNumeroProtocolo() + " - " + tramiteDto.getAssunto().getDescricao() + " - " + tramiteDto.getInteressado().getNome());
				totalSucessoTransacao++;
			}	
		}
		
		return corpoEmail.toString();
	}
}