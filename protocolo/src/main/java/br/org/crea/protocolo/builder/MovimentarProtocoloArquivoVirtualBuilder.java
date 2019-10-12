package br.org.crea.protocolo.builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.org.crea.commons.converter.cadastro.funcionario.FuncionarioConverter;
import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.cadastro.TipoDocumentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.protocolo.MovimentoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.model.response.ResponseCadastroDocumentoDocflow;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.interfaceutil.FormatMensagensConsumer;
import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.report.TemplateReportEnum;
import br.org.crea.commons.service.ReportService;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class MovimentarProtocoloArquivoVirtualBuilder {

	@Inject ProtocoloConverter protocoloConverter;
	
	@Inject FuncionarioConverter funcionarioConverter;
	
	@Inject ProtocoloDao protocoloDao;
	
	@Inject MovimentoDao movimentoDao;

	@Inject InteressadoDao interessadoDao;
	
	@Inject TipoDocumentoDao tipoDocumentoDao;
	
	@Inject ReportService reportService;
	
	@Inject DocflowService docflowService;
	
	@Inject AuditoriaService auditaService;
	
	@Inject HelperMessages messages;
	
	@Inject FormatMensagensConsumer formatMensagem;
	
	private List<TramiteDto> protocolosEmTramite;
	
	private ProtocoloDto processo;
	
	private UserFrontDto usuarioResponsavel;
	
	/**
	 * Atende aos casos em que o processo do protocolo está digitalizado e o protocolo vinculado que teve origem no portal é físico.
	 * O protocolo será cadastrado no Docflow no mesmo destino de seu processo. Havendo sucesso, o protocolo será movimentado
	 * no Corporativo mantendo as mesmas informações do Docflow e passará a ser um e-processo. Depois desta ação o protocolo movimentado
	 * fica apto a ser anexado ao protocolo principal.  
	 * 
	 * Esta regra foi demandada pela área fim, pois havendo um protocolo físico vinculado a um processo digital impossibilita a juntada
	 * dos protocolos filhos que estão dentro do processo.
	 * 
	 * @param protocolo - protocolo principal que corresponde ao processo
	 * @param usuario
	 * @return this                 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * **/
	
	public MovimentarProtocoloArquivoVirtualBuilder movimentaProtocoloArquivoVirtual(ProtocoloDto protocoloPrincipal, UserFrontDto usuario, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IOException {
		
		processo = protocoloPrincipal;
		usuarioResponsavel = usuario;
		protocolosEmTramite = protocoloConverter.toListTramitacaoProtocoloDto(protocoloDao.getListProtocolosVirtuaisProcessoDigital(processo));
		 
		 validaSaidaArquivoVirtual()
	 	.setDestinoMovimentacaoProtocoloPortal()
	 	.setSituacaoMovimentacaoProtocoloPortal()
	 	.setFuncionarioMovimentacaoProtocoloPortal(usuario)
	 	.geraMovimento(request)
	 	.validarMensagensMovimentacao();
		 
		return this;
		
	}
	
	/**
	 * Verifica se o protocolo vinculado ao processo principal está apto a ser movimentado de acordo com as 
	 * regras definidas para movimentar um protocolo do arquivo virtual.
	 * */
	public void verificaEstadoProtocoloArquivoVirtual() {
		
		protocolosEmTramite.forEach(p -> {
			
			if(  p.assuntoAptoASerDigitalizado() && p.protocoloEstaArquivoVirtual() && !p.ehDigital() ) {
				 p.setPossuiErros(false);
			} else {
				p.setPossuiErros(true);
				p.getMensagensDoTramite().add(messages.permissaoMoverProtocoloArquivoVirtual(p.getNumeroProtocolo()));
			}
		});
	}
	
	/**
	 * Verifica se o protocolo principal (processo) está digitalizado.
	 * */
	public void verificaDigitalizacaoProcesso() {
		
		protocolosEmTramite.forEach(p -> {
			
			if( !processo.isDigital() ) {
				 p.setPossuiErros(true);
				 p.getMensagensDoTramite().add(messages.permissaoMoverProtocoloArquivoVirtual(p.getNumeroProtocolo()));
			}
		});
	}
	
	/**
	 * Valida regras para retirar o protocolo do Arquivo Virtual.
	 * Para a correta execução da verificação, é necessário trabalhar os metódos
	 * na sequência que eles aparecem: verificaEstadoProtocoloArquivoVirtual(),
	 * verificaVinculoComProcesso() e verificaDigitalizacaoProcesso().
	 * */
	public MovimentarProtocoloArquivoVirtualBuilder validaSaidaArquivoVirtual() {
		
		verificaEstadoProtocoloArquivoVirtual();
		verificaVinculoComProcesso();
		verificaDigitalizacaoProcesso();
		return this;
	}
	
	/**
	 * Finaliza a movimentação em ambos os sistemas Corporativo e Docflow.
	 * @param request
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * */
	public MovimentarProtocoloArquivoVirtualBuilder geraMovimento(HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IOException {
		
		for (TramiteDto dto : protocolosEmTramite) {
			finalizaMovimentacaoDocflow(request, dto);
			finalizaMovimentacaoCorporativo(dto);
		}
		return this;
	}
	
	/**
	 * Verifica se o protocolo que será movimentado pertence ao processo principal
	 * Aqui já está previsto o caso de quando protocolo inicial tem ou não número e processo iguais.
	 * */
	public void verificaVinculoComProcesso() {
		
		Protocolo protocoloInicial = protocoloDao.getNumeroProtocoloInicial(processo);
		protocolosEmTramite.forEach(p -> {
			
			if( !p.getNumeroProcesso().equals(protocoloInicial.getNumeroProcesso())) {
				 p.setPossuiErros(true);
				 p.getMensagensDoTramite().add(messages.vinculoProtocoloArquivoVirtual(p.getNumeroProtocolo(), processo.getNumeroProtocolo()));
			}
		});
	}
	
	/**
	 * Seta o funcionário responsável pela retirada do protocolo do arquivo virtual
	 * @param usuario
	 * @return this
	 * */
	public MovimentarProtocoloArquivoVirtualBuilder setFuncionarioMovimentacaoProtocoloPortal(UserFrontDto usuario) {
		
		protocolosEmTramite.forEach(p -> p.setFuncionarioTramite(funcionarioConverter.toDto(usuario))); 
		return this;
	}
	
	/**
	 * Seta como destino do protocolo que está no arquivo virtual, o departamento onde se localiza seu processo.
	 * @return this
	 * */
	public MovimentarProtocoloArquivoVirtualBuilder setDestinoMovimentacaoProtocoloPortal() {
		
		protocolosEmTramite.forEach(p -> p.setIdDepartamentoDestino(processo.getUltimoMovimento().getIdDepartamentoDestino()));
		return this;
	}
	
	/**
	 * Seta a situação do movimento que será criado na saída do arquivo virtual.
	 * A situação será a mesma do movimento anterior.
	 * @return this
	 * */
	public MovimentarProtocoloArquivoVirtualBuilder setSituacaoMovimentacaoProtocoloPortal() {
		
		protocolosEmTramite.forEach(p -> p.setIdSituacaoTramite(p.getUltimoMovimento().getSituacao().getId()));
		return this;
	}
	
	/**
	 * Fecha o recebimento do protocolo virtual caso esteja em aberto, afim de tornar possível sua movimentação.
	 * @return this
	 * */
	public MovimentarProtocoloArquivoVirtualBuilder receberProtocoloArquivoVirtual(TramiteDto dto) {
		
		if( dto.getUltimoMovimento().getDataRecebimento() == null) {
			movimentoDao.recebeUltimoMovimento(dto.getUltimoMovimento().getId(), dto.getFuncionarioTramite().getId());
		}
		
		return this;
	}
	
	/**
	 * Finaliza a movimentação do protocolo virtual para o departamento de seu processo, marca como um e-processo
	 * e efetiva o recebimento do movimento que foi criado.
	 * @return this
	 * */
	public MovimentarProtocoloArquivoVirtualBuilder finalizaMovimentacaoCorporativo(TramiteDto dto) {
		
		if( !dto.isPossuiErros() ) {
			
			receberProtocoloArquivoVirtual(dto);
			
			Movimento movimentoSaidaArquivoVirtual = movimentoDao.gerarMovimentoProtocolo(dto, dto.getNumeroProtocolo());
			movimentoDao.recebeUltimoMovimento(movimentoSaidaArquivoVirtual.getId(), dto.getFuncionarioTramite().getId());
			
			movimentoSaidaArquivoVirtual.getProtocolo().setDigital(true);
			movimentoSaidaArquivoVirtual.getProtocolo().setDataDigitalizacao(new Date());
			protocoloDao.update(movimentoSaidaArquivoVirtual.getProtocolo());
			
			dto.setPossuiErros(false);
			dto.getMensagensDoTramite().add(messages.movimentoProtocoloArquivoVirtual(dto.getNumeroProtocolo()));
			
			auditaService
			  .usuario(usuarioResponsavel)
		      .acao("T")
		      .modulo(ModuloSistema.CORPORATIVO)
		      .numero(String.valueOf(dto.getNumeroProtocolo()))
		      .departamentoDestino(dto.getIdDepartamentoDestino())
		      .tipoEvento(TipoEventoAuditoria.TRAMITAR_PROTOCOLO)
		      .dtoClass(TramiteDto.class.getSimpleName())
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dataCriacao(new Date())
		      .textoAuditoria(messages.auditaMovimentoArquivoVirtual(dto.getNumeroProtocolo())).create();
		}
		
		return this;
	}
	
	/**
	 * Digitaliza o protocolo, efetuando o cadastro no Docflow do protocolo na mesma unidade de seu processo.
	 * Caso o processo de cadastro deste protocolo falhe no Docflow o documento gerado com base no processo será
	 * excluído para possibilitar nova tentativa do usuário.
	 *  
	 * @return this
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * */
	public MovimentarProtocoloArquivoVirtualBuilder finalizaMovimentacaoDocflow(HttpServletRequest request, TramiteDto dto) throws IllegalArgumentException, IllegalAccessException, IOException {
		ResponseCadastroDocumentoDocflow responseCadastroDocumento = new ResponseCadastroDocumentoDocflow();

		if( !dto.isPossuiErros() ) {
			
			InputStream documentoPdf = getDocumentoProtocoloDocflow(request);
			DocflowGenericDto genericDocflow = populaGenericDocflow(dto, documentoPdf);
			
			responseCadastroDocumento = docflowService.cadastrarProtocoloEletronico(genericDocflow);
			setMensagemMovimentoDocflow(dto, responseCadastroDocumento, genericDocflow);
			
			auditaService
			  .usuario(usuarioResponsavel)
		      .acao("C")
		      .modulo(ModuloSistema.CORPORATIVO)
		      .numero(String.valueOf(dto.getNumeroProtocolo()))
		      .departamentoDestino(dto.getIdDepartamentoDestino())
		      .tipoEvento(TipoEventoAuditoria.CADASTRAR_PROTOCOLO)
		      .dtoClass(TramiteDto.class.getSimpleName())
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dataCriacao(new Date())
		      .textoAuditoria(messages.cadastroProtocoloArquivoVirtualDocflow(dto.getNumeroProtocolo())).create();
		}
		return this;
	}
	
	/**
	 * Gera o documento que será inserido no protocolo digitalizado no Docflow.
	 * O texto do documento foi definido pela CDOC de acordo com o assunto do protocolo.
	 * @return documento
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * */
	public InputStream getDocumentoProtocoloDocflow(HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IOException {
		
		for (TramiteDto dto : protocolosEmTramite) {
			
			switch ( dto.getAssunto().getId().intValue() ) {
			case 2012:
				return reportService.getDocumentoInputStream(request, getDocumentoProtocoloBaixaQuadroTecnico(dto, TemplateReportEnum.DOCUMENTO_BAIXA_QUADRO_TECNICO));
			case 1006:
				return reportService.getDocumentoInputStream(request, getDocumentoProtocoloSegundaViaCarteira(dto, TemplateReportEnum.DOCUMENTO_SEGUNDA_VIA_CARTEIRA));
			default:
				break;
			}
		}
		return null;
	}
	
	/**
	 * Retorna conteúdo do documento que será gerado pela jasper report e que será cadastrado no protocolo do Docflow.
	 * para o protocolo de baixa de quadro técnico 
	 * @return documento
	 * */
	public DocumentoDto getDocumentoProtocoloBaixaQuadroTecnico(TramiteDto dto, TemplateReportEnum template) {
		DocumentoDto documento = new DocumentoDto();
		
		documento.setTextoPrincipal(messages.textoProtocoloVirtualBaixaQuadroTecnico(dto));
		documento.setTemplate(template);
		documento.setAssinatura("");
		return documento;
		
	}
	
	/**
	 * Retorna conteúdo do documento que será gerado pela jasper report e que será cadastrado no protocolo do Docflow.
	 * para o protocolo de segunda via carteira.
	 * @return documento
	 * */
	public DocumentoDto getDocumentoProtocoloSegundaViaCarteira(TramiteDto dto, TemplateReportEnum template) {
		DocumentoDto documento = new DocumentoDto();
		
		documento.setTextoPrincipal(messages.textoProtocoloVirtualEntregaCarteira(dto));
		documento.setTemplate(template);
		documento.setAssinatura("");
		return documento;
	}
	
	/**
	 * Verifica a ocorrência de erro no cadastro do protocolo no Docflow.
	 * @param dto
	 * @param responseDocflow
	 * */
	public void setMensagemMovimentoDocflow(TramiteDto dto, ResponseCadastroDocumentoDocflow responseDocflow, DocflowGenericDto genericDocflow) {

		if( responseDocflow.hasError() ) {

			dto.setPossuiErros(true);
			dto.getMensagensDoTramite().add(messages.erroCadastroProtocolo(dto.getNumeroProtocolo(), responseDocflow.getMessage().getValue()));
		}
	}
	
	/**
	 * Preenche o objeto com dados obrigatórios para cadastrar um protocolo eletrônico acessando a api do Docflow.
	 * Obs. Unidade destino e unidade origem no cadastro do protocolo no Docflow devem ser as mesmas para que 
	 * não seja aberto um trâmite para o protocolo no ato ato do cadastro.
	 * @return dto
	 * */
	public DocflowGenericDto populaGenericDocflow(TramiteDto dto, InputStream documentoPdf) {
		DocflowGenericDto docflowDto = new DocflowGenericDto();
		
		docflowDto.setTipoDocumento("59");
		docflowDto.setMatricula(dto.getFuncionarioTramite().getMatricula().toString());
		docflowDto.setAssunto(dto.getAssunto().getDescricao());
		docflowDto.setInteressado(dto.getPessoa().getNome());
		docflowDto.setObservacao("");
		docflowDto.setDataArquivo(DateUtils.format(new Date(), DateUtils.DD_MM_YYYY_HH_MM_SS_SSS));
		docflowDto.setUnidadeOrigem(dto.getIdDepartamentoDestino().toString());
		docflowDto.setUnidadeDestino(dto.getIdDepartamentoDestino().toString());
		docflowDto.setCodigoDepartamento(dto.getIdDepartamentoDestino().toString());
		docflowDto.setNomeArquivoPdf("documento-comprobatorio");
		docflowDto.setNumeroProtocolo(dto.getNumeroProtocolo().toString());
		docflowDto.setNumeroProcesso(dto.getNumeroProcesso().toString());
		docflowDto.setTipoProcesso(dto.getTipoProtocolo().getDigito().toString());
		docflowDto.setInputStreamArquivoPdf(documentoPdf);
		
		return docflowDto;
	}

	/** Remove as mensagens vazias da validação do protocolo
	 * @return this
	 */
	public MovimentarProtocoloArquivoVirtualBuilder validarMensagensMovimentacao() {
		
		protocolosEmTramite.forEach(p -> formatMensagem.accept(p.getMensagensDoTramite()));
		return this;
	}
	
	/**
	 * Retorna objetos em trâmite contendo o resultado da operação.
	 * @return protocolosEmTramite
	 * */
	public List<TramiteDto> build() {
		return protocolosEmTramite;
	}
	
}
