package br.org.crea.commons.builder.protocolo.validaterules;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.funcionario.FuncionarioConverter;
import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.dao.fiscalizacao.RecursoAutuacaoDao;
import br.org.crea.commons.dao.protocolo.EprocessoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.dto.ProtocoloDocflowDto;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.interfaceutil.FormatMensagensConsumer;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.Eprocesso;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.fiscalizacao.RecursoAutuacao;
import br.org.crea.commons.models.protocolo.dtos.TramitacaoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;
import br.org.crea.commons.service.cadastro.DepartamentoService;
import br.org.crea.commons.service.profissional.ProfissionalService;


public class ValidaEnvioProtocoloBuilder {

	@Inject ProtocoloDao protocoloDao;
	
	@Inject FuncionarioDao funcionarioDao;
	
	@Inject EmpresaDao empresaDao;
	
	@Inject FinDividaDao finDividaDao;
	
	@Inject DepartamentoDao departamentoDao;
	
	@Inject RecursoAutuacaoDao recursoAutuacaoDao;
	
	@Inject ProtocoloConverter protocoloConverter;
	
	@Inject FuncionarioConverter funcionarioConverter;
	
	@Inject DocflowService docflowService; 
	
	@Inject HelperMessages messages;
	
	@Inject ValidaGenericTramiteProtocoloBuilder validateGenericTramite;
	
	@Inject EprocessoDao eprocessoDao;
	
	@Inject FormatMensagensConsumer formatMensagensValidacao;
	
	@Inject ProfissionalService profissionalService;
	
	@Inject DepartamentoService departamentoService;
	
	private List<TramiteDto> listTramiteDto;
	
	private boolean destinoTramiteEhArquivo;
	
	private boolean destinoEhSiacol;
	
	private boolean destinoTramiteExecutaJulgamentoRevelia;
	
	private DocflowGenericDto genericDocflowDto;
	
	/** Método que executa as validações da lista de protocolos
	 * 
	 * Essa classe aplica um padrão de projeto chamado Builder, o qual encadeia a chamada dos métodos, retornando a própria classe (this).
	 * 
	 * O dto da classe TramitacaoProtocoloDto está sendo passado e contém numa lista dos protocolos a serem tramitados. Nesta lista
	 * são informados os números do protocolos. Na primeira etapa de validação estes protocolos serão buscados no banco 
	 * e preenchidos numa lista de TramiteDto para serem trabalhados.
	 * 
	 * Verifica se o departamento de destino é arquivo ou arquivo virtual.
	 * 
	 * Verifica se o departamento pai de destino segue a regra de julgamento revelia.
	 * 
	 * Define no TramiteDto o departamento de destino do protocolo e o funcionário que está tramitando. 
	 * 
	 * Faz o build, validando as seguintes condições nesta ordem e atribuindo mensagens de erro quando for o caso: 
	 * estado do protocolo, origem e destino do tramite, protocolo infração para julgamento,
	 * protocolo eletronico, mensagens dos protocolos.
	 * 
	 * @param dto - contém uma lista de um ou mais protocolos, funcionário que tramitou, departamento de destino 
	 *              e departamento pai em relação ao de destino (útil para validação de algumas regras).
	 * @param usuario - token
	 * @return this - retorna a própria classe que contém a lista de TramiteDto validados
	 */
	public ValidaEnvioProtocoloBuilder validarEnvio(TramitacaoProtocoloDto dto, UserFrontDto usuario) {
		
		listTramiteDto = protocoloConverter.toListTramitacaoProtocoloDto(protocoloDao.getListProtocolosEmTramite(dto.getListProtocolos()));
		
		destinoTramiteEhArquivo = dto.destinoTramiteEhArquivo() || dto.destinoTramiteEhArquivoVirtual();
		destinoTramiteExecutaJulgamentoRevelia = dto.destinoTramiteExecutaJulgamentoRevelia();
		destinoEhSiacol = destinoEhSiacol(dto.getIdDepartamentoDestino());
		
		setDepartamentoDestinoProtocolos(dto.getIdDepartamentoDestino());
		setFuncionarioDaTramitacao(funcionarioConverter.toDto(usuario));
		
		 validarEstadoDoProtocolo()
		.validarOrigemEDestinoDoTramite()
		.validarProtocoloInfracaoParaJulgamento()
		.validarProtocoloEletronico()
		.validarMensagensProtocolo();
		return this;
	}
	
	/** 
	 * Valida o estado de cada protocolo da lista, verificando os seguintes estados do protocolo e 
	 *  atribuindo mensagens de erro ao TramiteDto daquele protocolo quando for o caso:
	 *  excluído, substituído, anexado, apensado, inventariado e pendente de recebimento.
	 * 
	 * @return this
	 */
	public ValidaEnvioProtocoloBuilder validarEstadoDoProtocolo() {
		
		for (TramiteDto dto : listTramiteDto) {
			
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloExcluido(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setmensagemParaProtocoloSubstituido(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloAnexado(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloApensado(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloInventariado(dto));
			dto.getMensagensDoTramite().add(setMensagemParaProtocoloTramitePendenteRecebimento(dto));
		}
		
		return this;
	}
	
	/** 
	 * Valida a origem e destino do trâmite de cada protocolo da lista, segundo as seguintes condições:
	 * protocolo com origem e destino iguais,
	 * departamento de destino indeterminado,
	 * permissão do funcionário para tramitar a partir do departamento de origem,
	 * protocolo de registro provisório (de empresa enviado para arquivo),
	 * protocolo de auto de infração (enviado para arquivo)
	 * 
	 * @return this
	 */
	public ValidaEnvioProtocoloBuilder validarOrigemEDestinoDoTramite() {
		
		for (TramiteDto dto : listTramiteDto) {
			
			dto.getMensagensDoTramite().add(setMensagemProtocoloComOrigemEDestinoIguais(dto));
			dto.getMensagensDoTramite().add(setMensagemDepartamentoDestinoIndeterminado(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemPermissaoFuncionarioNaUnidadeProtocolo(dto));
			dto.getMensagensDoTramite().add(setMensagemArquivarProtocoloRegistroProvisorio(dto));
			dto.getMensagensDoTramite().add(setMensagemArquivarProtocoloAutoInfracao(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloGerenciadoPeloSiacol(dto));
		}
		
		return this;
	}
	
	/** 
	 * Valida se departamento pai de destino é câmara especializada ou coordenacao de apoio aos colegiados
	 *  e atualiza recurso para julgamento em revelia 
	 * 
	 * @return this
	 */
	public ValidaEnvioProtocoloBuilder validarProtocoloInfracaoParaJulgamento() {
		
		for (TramiteDto dto : listTramiteDto) {
			
			if(protocoloEstaEmUnidadeParaEnvioJulgamentoARevelia(dto) && destinoTramiteExecutaJulgamentoRevelia) {
				atualizaRecursoParaJulgamentoRevelia(dto);
			}
		}
		
		return this;
	}
	
	/** 
	 *  Se o protocolo for eletrônico, valida as seguintes condições:
	 *  localizaçãoEprocesso (verifica se movimento está divergente do corporativo), 
	 *  funcionarioTramiteEprocesso (verifica se o funcionário está alocado naquela unidade), 
	 *  unidadePadraoTramiteEprocesso (verifica se unidade padrão do funcionário no docflow difere do corporativo), 
	 *  protocoloNaoRecebidoEprocesso, 
	 *  EprocessoBloqueado
	 * 
	 * @return this
	 */
	public ValidaEnvioProtocoloBuilder validarProtocoloEletronico() {
		
		for (TramiteDto dto : listTramiteDto) {
			
			if(dto.ehDigital() ) {
				DocflowGenericDto dtoGenerico = new DocflowGenericDto();
				dtoGenerico.setNumeroProtocolo(String.valueOf(dto.getNumeroProtocolo()));
				
				ProtocoloDocflowDto resultado = docflowService.consultarProtocolo(dtoGenerico);
				if(resultado.getMessageError() != null){
					dto.getMensagensDoTramite().add(resultado.getMessageError());
					dto.setPossuiErros(true);
					return this;
				}
				
				dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemLocalizacaoEprocesso(dto));
				dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemFuncionarioTramiteEprocesso(dto));
				dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemUnidadePadraoTramiteEprocesso(dto));
				dto.getMensagensDoTramite().add(setMensagemProtocoloNaoRecebidoEprocesso(dto));
				dto.getMensagensDoTramite().add(setEProcessoBloqueado(dto));
			}
		}
		
		return this;
	}	
	
	/** 
	 *  Verifica se departamento de origin é igual ao de destino
	 *  e define mensagem de erro se for o caso 
	 * 
	 * @param dto
	 * @return mensagem 
	 */
	public String setMensagemProtocoloComOrigemEDestinoIguais(TramiteDto dto) {
	
		String mensagem = "";
		
		if(dto.getUltimoMovimento().getIdDepartamentoDestino().equals(dto.getIdDepartamentoDestino())) {
			
			dto.setPossuiErros(true);
			mensagem = messages.protocoloOrigemEDestinoIguais(dto.getNumeroProtocolo());	
		
		}
		return mensagem;
	}
	
	/** 
	 *  Verifica se protocolo está pendente de recebimento
	 * 	e define mensagem de erro se for o caso
	 * 
	 * @param dto
	 * @return mensagem
	 */
	public String setMensagemParaProtocoloTramitePendenteRecebimento(TramiteDto dto) {
		String mensagem = "";
		
		if(dto.getUltimoMovimento().getDataRecebimento() == null) {
			
			dto.setPossuiErros(true);
			mensagem = messages.protocoloTramitePendenteRecebimento(dto.getNumeroProtocolo());	
		}
		return mensagem;
	}
	
	/** 
	 *  Verifica se departamento de destino está como indeterminado 
	 *  e define mensagem de erro se for o caso.
	 *  
	 * @param dto
	 * @return mensagem
	 */
	public String setMensagemDepartamentoDestinoIndeterminado(TramiteDto dto) {
		String mensagem = "";
		
		if(dto.getUltimoMovimento().getIdDepartamentoDestino().equals(new Long(999))) {
			
			dto.setPossuiErros(true);
			mensagem = messages.protocoloDestinoIndeterminado(dto.getNumeroProtocolo());
		}
		
		return mensagem;
		
	}
	
	/** 
	 *  Verifica se o departamento de destino do protocolo é arquivo e se é de registro provisório de empresa
	 *  e define mensagem de erro se for o caso.
	 * 
	 * @param dto
	 * @return mensagem
	 */
	public String setMensagemArquivarProtocoloRegistroProvisorio(TramiteDto dto) {
		String mensagem = "";
		
		if(destinoTramiteEhArquivo && dto.getTipoProtocolo().equals(TipoProtocoloEnum.EMPRESA)) {
			
			Empresa empresa = empresaDao.getEmpresaPor(dto.getNumeroProtocolo());
			
			if(empresa.temRegistroProvisorio() ){
				dto.setPossuiErros(true);
				mensagem =  messages.arquivoProtocoloRegistroProvisorio(dto.getNumeroProtocolo());
			}
		}
		
		return mensagem;
	}
	
	/** 
	 *  Verifica se o destino é arquivo e de protocolo é de auto de infração
	 *  e define mensagem de erro se for o caso.
	 * 
	 * @param dto
	 * @return mensagem
	 */
	public String setMensagemArquivarProtocoloAutoInfracao(TramiteDto dto) {
		String mensagem = "";
		
		if(destinoTramiteEhArquivo && (dto.getTipoProtocolo().equals(TipoProtocoloEnum.AUTOINFRACAO)) || dto.getTipoProtocolo().equals(TipoProtocoloEnum.AUTOINFRACAO_EXTERNO) ) {
			dto.setPossuiErros(true);
			mensagem = messages.arquivoProtocoloAutoInfracao(dto.getNumeroProtocolo());
		}
		
		return mensagem;
	}
	
	/** 
	 *  Verifica se o protocolo não está recebido no docflow
	 *  e define mensagem de erro se for o caso.
	 * 
	 * @param dto
	 * @return mensagem
	 */
	public String setMensagemProtocoloNaoRecebidoEprocesso(TramiteDto dto) {
		String mensagem = "";
		genericDocflowDto.setNumeroProtocolo(dto.getNumeroProtocolo().toString());
		
		boolean protocoloEstaRecebido = docflowService.protocoloEstaRecebidoDocflow(genericDocflowDto);
		
		if(!protocoloEstaRecebido) {
			dto.setPossuiErros(true);
			mensagem = messages.tramitePendenteRecebimentoDocflow(dto.getNumeroProtocolo());
		}
		
		return mensagem;
	}
	
	/** 
	 * Verifica se existe dívida de auto de infração para o protocolo
	 * @param dto
	 * @return true (existe dívida) ou false (não existe dívida)
	 */
	public boolean existeDividaAutoInfracaoParaProtocolo(TramiteDto dto) {
		
		FinDivida divida = finDividaDao.buscaDividaAutoInfracaoPor(dto.getNumeroProtocolo().toString(), dto.getIdPessoa()); 
		return divida != null && divida.getStatus().getQuitado().equals(new Long(0)) ? true : false;
	}
	
	/** 
	 * Verifica se protocolo está em unidade para envio para julgamento a revelia
	 * @param dto
	 * @return true ou false
	 */
	public boolean protocoloEstaEmUnidadeParaEnvioJulgamentoARevelia(TramiteDto dto) {
		return departamentoDao.departamentoTramitaParaJulgamentoRevelia(dto.getUltimoMovimento().getIdDepartamentoDestino());
	}
	
	/**
	 * Retorna true se o departamento destino estiver sendo controlado pelo Siacol
	 * @param idDepartamentoDestino
	 * @return true / false
	 * */
	public boolean destinoEhSiacol(Long idDepartamentoDestino) {
		String modulo = departamentoDao.buscaDepartamentoPor(idDepartamentoDestino).getModulo();
		
		return modulo != null && modulo.equals("SIACOL") ? true : false; 
	}
	
	/** Atribuir o departamento de destino a todos os movimentos dos protocolos
	 * Para os casos de tramite para o arquivo geral ou virtual, será verificado se o interessado é técnico e sua modalidade, 
	 * podendo assim alterar o destino.
	 * @param idDepartamentoDestino
	 */
	public void setDepartamentoDestinoProtocolos(Long idDepartamentoDestino) {
		
		for (TramiteDto dto : listTramiteDto) {
			Long destinoDepTecnico = retornarDestinoProtocoloInteressadoProfissionalTecnico(dto);
			
			dto.setIdDepartamentoDestino(destinoDepTecnico != 0 ? destinoDepTecnico : idDepartamentoDestino);
			dto.setDestinoEhSiacol(destinoEhSiacol);
		}
	}
	
	/** Atribui o funcionário responsável pela tramitação de todos os protocolos
	 *  @param funcionario
	 */
	public void setFuncionarioDaTramitacao(FuncionarioDto funcionario) {
		
		Departamento departamentoFuncionario = departamentoDao.getBy(funcionario.getIdDepartamento());
		
		if( departamentoFuncionario.getModuloDepartamento() != null ) {
			
			funcionario.setSiacol(departamentoFuncionario.getModuloDepartamento().equals(ModuloSistema.SIACOL) ? true : false);
			
		} else {
			
			funcionario.setSiacol(false);
		}
		
		genericDocflowDto = new DocflowGenericDto();
		genericDocflowDto.setMatricula(funcionario.getMatricula().toString());
		listTramiteDto.forEach(f -> f.setFuncionarioTramite(funcionario));
	}
	
	/** 
	 *  Verifica se o Eprocesso está bloqueado
	 *  e define mensagem de erro se for o caso.
	 * @param dto
	 * @return mensagem 
	 */
	public String setEProcessoBloqueado(TramiteDto dto) {
		
		String mensagem = "";
		
		Eprocesso eprocesso = eprocessoDao.getBy(1L);
		
		if(!eprocesso.estaLiberado() || !eprocesso.podeTramitar()) {
			dto.setPossuiErros(true);
			mensagem = messages.eprocessoNaoLiberadoParaTramiteEnvio();
		}
		
		return mensagem;
	}
	
	/** 
	 *  Atualiza recurso para julgamento a revelia
	 * 	Obtém lista de recursos da autuação do protocolo.
	 *  Se existem recursos, cadastra recursos para julgamento e atualiza dívida em recurso. 
	 * @param dto
	 */
	public void atualizaRecursoParaJulgamentoRevelia(TramiteDto dto) {
		
		List<RecursoAutuacao> listRecursos = recursoAutuacaoDao.getListRecursosPor(dto.getNumeroProtocolo());
		if(!listRecursos.isEmpty()) {
			
			recursoAutuacaoDao.cadastraRecursoParaJulgamentoReveliaPor(dto.getNumeroProtocolo(), dto.getFuncionarioTramite().getId());
			finDividaDao.atualizaStatusDividaEmRecursoPor(dto.getNumeroProtocolo());
		}
	}
	
	/** 
	 * Caso esteja enviando para o arquivo geral ou virtual um protocolo, onde o interessado seja um profissional técnico,
	 * deverá transferir para outro setor, dependendo da modalidade do profissional.
	 * A regra foi demandada pelo CDOC em conjunto com o Jeferson em 04/2018.
	 * @param protocolo
	 * @return idDestino
	 */
	private Long retornarDestinoProtocoloInteressadoProfissionalTecnico(TramiteDto dto){
		
		Long idDestino = 0L;
		
		Protocolo protocolo = protocoloDao.getProtocoloBy(dto.getNumeroProtocolo());
		
		if(protocolo.getPessoa().getId() != null && protocolo.getPessoa().getId() > 0L && destinoTramiteEhArquivo){
			idDestino = departamentoService.retornarDestinoProtocoloInteressadoProfissionalTecnico(protocolo.getPessoa().getId());
		}
		
		return idDestino;
	}
	
	/** Remove as mensagens vazias do protocolo
	 * @return this
	 */
	public ValidaEnvioProtocoloBuilder validarMensagensProtocolo() {

		listTramiteDto.forEach(p -> formatMensagensValidacao.accept(p.getMensagensDoTramite()));
		return this;
	}
	
	/** 
	 * Retorna lista de TramiteDto define nesta classe
	 * @return lista de TramiteDto
	 */
	public List<TramiteDto> build() {
		return listTramiteDto;
	}
}
