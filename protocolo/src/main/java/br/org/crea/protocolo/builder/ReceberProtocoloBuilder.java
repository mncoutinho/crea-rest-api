package br.org.crea.protocolo.builder;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.MovimentoProtocoloConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.dao.financeiro.FinTermoInscricaoDao;
import br.org.crea.commons.dao.financeiro.GeradorSequenciaOficioDao;
import br.org.crea.commons.dao.financeiro.RlTermoInscricaoDividaDao;
import br.org.crea.commons.dao.protocolo.MovimentoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.docflow.converter.DocflowGenericConverter;
import br.org.crea.commons.docflow.model.response.ResponseTramiteProtocoloDocflow;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.factory.AuditaProtocoloFactory;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.financeiro.FinTermosInscricao;
import br.org.crea.commons.models.financeiro.enuns.FinFase;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.financeiro.FinDividaService;
import br.org.crea.commons.util.EmailUtil;
import br.org.crea.commons.util.StringUtil;

public class ReceberProtocoloBuilder {

	@Inject MovimentoDao movimentoDao;

	@Inject ProtocoloDao protocoloDao;

	@Inject DepartamentoDao departamentoDao;

	@Inject FinDividaDao finDividaDao;

	@Inject FinTermoInscricaoDao termoInscricaoDao;

	@Inject GeradorSequenciaOficioDao geradorSequenciaOficioDao;

	@Inject RlTermoInscricaoDividaDao rlTermoDividaDao;

	@Inject FinDividaService dividaService;

	@Inject DocflowService docflowService;

	@Inject MovimentoProtocoloConverter movimentoProtocoloConverter;
	
	@Inject DocflowGenericConverter docflowConverter;

	@Inject HelperMessages messages;

	@Inject HttpClientGoApi httpGoApi;

	@Inject AuditaProtocoloFactory auditFactory;
	
	@Inject EmailService emailService;
	
	@Inject FuncionarioDao funcionarioDao;
	
	@Inject EmailUtil emailUtil;

	private List<TramiteDto> listTramiteDto;
	
	int totalErro = 0;

	private UserFrontDto usuarioDto;
	
	private ModuloSistema moduloSistema;
	

	/**
	 * Efetua de fato a movimentação dos protocolos validados, gravando as
	 * alterações nos bancos (corporativo e docflow)
	 * 
	 * Para cada protocolo sem erros, define o status como "em recebimento" e
	 * grava a movimentação no docflow e corporativo.
	 * 
	 * @param listDto
	 *            - contém uma lista de protocolos e suas informações de seu
	 *            trâmite
	 * @return this - retorna a própria classe que contém a lista de TramiteDto
	 *         validados
	 */
	public ReceberProtocoloBuilder gerarRecebimentoProtocolo(ModuloSistema modulo, List<TramiteDto> listDto, UserFrontDto usuario) {
		usuarioDto = usuario;
		moduloSistema = modulo;
		
		listTramiteDto = listDto;
		for (TramiteDto dto : listTramiteDto) {

			if (!dto.isPossuiErros()) {

				setStatusProtocolosEmRecebimento(dto.getNumeroProtocolo());
				receberProtocoloDocflow(dto).receberProtocoloCorporativo(dto);
				auditFactory.auditaAcaoProtocolo(dto, usuarioDto, getMensagemAuditoria(dto.getMensagensDoTramite()), moduloSistema, false);
			}			
		}
		
		enviarEmailTramiteErro(usuario);
		
		return this;
	}

	private String getMensagemAuditoria(List<String> mensagensDoTramite) {
		for (String mensagem : mensagensDoTramite) {
			if (!mensagem.equals("")) {
				return mensagem;
			}
		}
		return "";
	}

	/**
	 * Movimenta o protocolo no Docflow
	 * 
	 * Se o protocolo é digital e foi validado sem erros, envia informações do
	 * movimento de recebimento para serem gravadas no docflow e adiciona
	 * mensagem de erro no dto se houver.
	 * 
	 * @param dto
	 * @return this
	 */
	public ReceberProtocoloBuilder receberProtocoloDocflow(TramiteDto dto) {
		ResponseTramiteProtocoloDocflow responseDocflow = new ResponseTramiteProtocoloDocflow();

		if (dto.isProtocoloEhDigital() && !dto.isPossuiErros()) {
			
			responseDocflow = docflowService.receberProtocolo(docflowConverter.toDocFlowGenericDto(dto));
			
			setMensagemTramiteDocflow(dto, responseDocflow);
			if (!responseDocflow.hasError()) {
				auditFactory.auditaEnvioOrRecebimentoDocflow(dto, usuarioDto, moduloSistema, false);
			}
			
		}

		return this;
	}

	/**
	 * Movimenta o protocolo no Corporativo
	 * 
	 * Se o protocolo não possui erros, executa as seguintes ações:
	 * 
	 * grava a movimentação do protocolo, adiciona mensagem de confirmação de
	 * recebimento do protocolo,
	 * 
	 * se o protocolo é destinado a dívida ativa e é de auto de infração,
	 * atualiza dívida ativa ao receber protocolo e havendo mensagem de retorno
	 * adiciona ao dto,
	 * 
	 * grava a movimentação dos protocolos anexos, grava a movimentação dos
	 * protocolos apensos, define no dto que não possui erros, define status do
	 * protocolo como normal
	 * 
	 * @param dto
	 * @return this
	 */
	public ReceberProtocoloBuilder receberProtocoloCorporativo(TramiteDto dto) {

		if (!dto.isPossuiErros()) {

			Movimento movimento = movimentoDao.recebeUltimoMovimento(dto.getUltimoMovimento().getId(), dto.getFuncionarioTramite().getId());
			dto.getMensagensDoTramite().add(messages.confirmacaoRecebimentoProtocolo(dto.getNumeroProtocolo(), movimento.getDepartamentoDestino().getNome()));
			
			if( movimento.getDepartamentoDestino().isDividaAtiva() && movimento.getProtocolo().getTipoProtocolo().equals(TipoProtocoloEnum.AUTOINFRACAO)) {
				
				String respostaInclusaoDiati = atualizaDividaAtivaAoReceberProtocolo(movimento.getProtocolo());

				if (!respostaInclusaoDiati.equals("")) {
					dto.getMensagensDoTramite().add(atualizaDividaAtivaAoReceberProtocolo(movimento.getProtocolo()));
				}
			}

			receberProtocolosAnexos(dto);
			receberProtocolosApensos(dto);

			dto.setPossuiErros(false);
		}
		normalizaStatusProtocolo(dto.getNumeroProtocolo());
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
			httpGoApi.geraLog("EnviarProtocoloBuilder || setStatusNormalProtocolosTramitados", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
	}

	/**
	 * Atualiza a dívida ao receber protocolo no caso em que o protocolo é
	 * destinado a dívida ativa e é de auto de infração
	 * 
	 * Se existir dívida para gerar termo de inscrição relativo a este protocolo
	 * Obtém a dívida Se não existe termo de inscrição para dívida cria termo da
	 * inscrição cadastra termo de inscrição de dívida e atualiza dívida com
	 * data valor atualizado null e diati como true
	 * 
	 * retorna mensagem se houve inclusão na dívida ativa ou não
	 * 
	 * @param protocolo
	 * @return mensagem
	 */
	public String atualizaDividaAtivaAoReceberProtocolo(Protocolo protocolo) {
		String mensagem = "";

		if (finDividaDao.existeDividaParaGerarTermoInscricao(protocolo.getNumeroProtocolo().toString())) {
			FinDivida finDivida = finDividaDao.getDividaPor(protocolo.getNumeroProtocolo().toString());

			if (!termoInscricaoDao.existeTermoInscricaoParaDivida(finDivida.getId())) {

				FinTermosInscricao termoDivida = termoInscricaoDao.create(populaTermoDivida(protocolo, finDivida));
				rlTermoDividaDao.cadastraTermoInscricaoDivida(finDivida, termoDivida);

				finDivida.setDataValorAtualizado(null);
				finDivida.setDiati(true);
				finDividaDao.update(finDivida);
				mensagem = messages.confirmarInclusaoDividaAtiva();
			}

		} else {
			mensagem = messages.confirmarNaoInclusaoDividaAtiva();
		}
		return mensagem;
	}

	/**
	 * Retorna um termo de inscrição preenchido a partir do protocolo e dívida
	 * 
	 * @param protocolo
	 * @param finDivida
	 * @return termoInscricao
	 */
	public FinTermosInscricao populaTermoDivida(Protocolo protocolo, FinDivida finDivida) {
		FinTermosInscricao termoInscricao = new FinTermosInscricao();

		termoInscricao.setPessoa(protocolo.getPessoa());
		termoInscricao.setTipoPessoa(protocolo.getPessoa().getTipoPessoa());
		termoInscricao.setNatureza(finDivida.getNatureza());
		termoInscricao.setDataInclusao(new Date());
		termoInscricao.setFinFase(FinFase.FASE_AMIGAVEL);
		termoInscricao.setCodigoProcesso(null);
		termoInscricao.setObservacao("Inclusão de Dí­vida de AI");
		termoInscricao.setAtivo(true);
		termoInscricao.setNumeroTermo(geradorSequenciaOficioDao.getSequenciaNumeroTermoInscricao("RDA"));
		return termoInscricao;
	}

	/**
	 * Recebe os protocolos anexos
	 * @param dto
	 */
	public void receberProtocolosAnexos(TramiteDto dto) {

		for (ProtocoloDto anexo : dto.getListAnexos()) {

			movimentoDao.recebeUltimoMovimento(movimentoDao.buscaUltimoMovimentoPor(anexo.getNumeroProtocolo()).getId(), dto.getFuncionarioTramite().getId());
		}

	}

	/**
	 * Recebe os protocolos apensos
	 * @param dto
	 */
	public void receberProtocolosApensos(TramiteDto dto) {

		for (ProtocoloDto apenso : dto.getListApensos()) {

			movimentoDao.recebeUltimoMovimento(movimentoDao.buscaUltimoMovimentoPor(apenso.getNumeroProtocolo()).getId(), dto.getFuncionarioTramite().getId());
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
		} else {
			dto.getMensagensDoTramite().add("");
		}
	}

	/**
	 * Grava o status do protocolo como "em recebimento"
	 * @param numeroProtocolo
	 */
	public void setStatusProtocolosEmRecebimento(Long numeroProtocolo) {

		try {

			Protocolo protocolo = protocoloDao.getProtocoloBy(numeroProtocolo);
			protocolo.setIdStatusTransacao(new Long(7));
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
	 * 	Quem envia: cdoc@crea-rj.org.br
	 * 	Quem recebe: funcionario do recebimento e / ou coordenação do funcionário + (backup) docflow@crea-rj.org.br  
	 * @param mensagens
	 * @param funcionario
	 */
	private void enviarEmailTramiteErro(UserFrontDto usuario){
		Funcionario funcionario = funcionarioDao.getBy(usuario.getIdFuncionario());
		
		try{
			String mensagem = messages.corpoEmailErrosTramiteEnvio(funcionario.getNome(), criarCorpoMensagemErro(), totalErro);
			
			if(totalErro > 0){
				EmailEnvioDto email = new EmailEnvioDto();
				email.setMensagem(mensagem);
				email.setAssunto("SIPRO - Envio de protocolos com erros");
				
				List<DestinatarioEmailDto> listaDestinatarioEmails = emailUtil.montarListaDestinatarios(funcionario.getPessoaFisica());
				
				if(!listaDestinatarioEmails.isEmpty()){
					email.setDestinatarios(listaDestinatarioEmails);
					email.setDestinatariosCC(emailUtil.montarListaDestinatarios("docflow@crea-rj.org.br", "BACKUP"));
				}else{
					email.setDestinatarios(emailUtil.montarListaDestinatarios("docflow@crea-rj.org.br", "BACKUP"));
				}		
				
				email.setEmissor("cdoc@crea-rj.org.br");
				email.setDataUltimoEnvio(new Date());
		
				emailService.envia(email);
			}	
		}catch(Exception e){
			httpGoApi.geraLog("EnviarProtocoloBuilder || enviarEmailTramiteErro", StringUtil.convertObjectToJson(funcionario), e);
		}
	}
	
	private String criarCorpoMensagemErro(){
		totalErro = 0;	
		StringBuilder corpoEmail = new StringBuilder();
		
		for(TramiteDto tramiteDto : listTramiteDto){
			if(tramiteDto.isPossuiErros()){
				tramiteDto.getMensagensDoTramite().forEach(mensagem->corpoEmail.append("<li>" + mensagem));
				totalErro++;
			}	
		}
		
		return corpoEmail.toString();
	}

}
