package br.org.crea.commons.builder.protocolo.validaterules;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.funcionario.FuncionarioConverter;
import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.protocolo.EprocessoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.dto.ProtocoloDocflowDto;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.interfaceutil.FormatMensagensConsumer;
import br.org.crea.commons.models.commons.Eprocesso;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.models.protocolo.dtos.TramitacaoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;

public class ValidaRecebimentoProtocoloBuilder {
	
	private List<TramiteDto> listTramiteDto;
	
	@Inject ProtocoloConverter protocoloConverter;
	
	@Inject FuncionarioConverter funcionarioConverter;
	
	@Inject ProtocoloDao protocoloDao;
	
	@Inject FuncionarioDao funcionarioDao;
	
	@Inject ValidaGenericTramiteProtocoloBuilder validateGenericTramite;
	
	@Inject HelperMessages messages;
	
	@Inject DocflowService docflowService;
	
	@Inject EprocessoDao eprocessoDao;
	
	@Inject DepartamentoDao departamentoDao;
	
	@Inject FormatMensagensConsumer formatMensagensValidacao;

	private DocflowGenericDto genericDocflowDto;
	

	/** Método que executa as validações da lista de protocolos
	 * 
	 * Essa classe aplica um padrão de projeto chamado Builder, o qual encadeia a chamada dos métodos, retornando a própria classe (this).
	 * 
	 * O dto da classe TramitacaoProtocoloDto está sendo passado e contém numa lista dos protocolos a serem tramitados. Nesta lista
	 * são informados os números do protocolos. Na primeira etapa de validação estes protocolos serão buscados no banco 
	 * e preenchidos numa lista de TramiteDto para serem trabalhados.
	 * 
	 * Define no TramiteDto o funcionário que está tramitando. 
	 * 
	 * Faz o build, validando as seguintes condições nesta ordem e atribuindo mensagens de erro quando for o caso: 
	 * estado do protocolo, protocolo eletronico, mensagens dos protocolos.
	 * 
	 * @param dto - contém uma lista de um ou mais protocolos, funcionário que tramitou, departamento de destino 
	 *              e departamento pai em relação ao de destino (útil para validação de algumas regras).
	 * @param usuario - token
	 * @return this - retorna a própria classe que contém a lista de TramiteDto validados
	 */
	public ValidaRecebimentoProtocoloBuilder validarRecebimento(TramitacaoProtocoloDto dto, UserFrontDto usuario) {
		listTramiteDto = protocoloConverter.toListTramitacaoProtocoloDto(protocoloDao.getListProtocolosEmTramite(dto.getListProtocolos()));
		
		setFuncionarioRecebimento(funcionarioConverter.toDto(usuario));
		validarEstadoDoProtocolo().validarProtocoloEletronico().validarMensagensProtocolo();
		
		return this;
	}

	/** Atribui o funcionário responsável pela tramitação de todos os protocolos
	 * 
	 * @param funcionario
	 */
	private void setFuncionarioRecebimento(FuncionarioDto funcionario) {
		listTramiteDto.forEach(f -> f.setFuncionarioTramite(funcionario));
	}
	
	/** Valida o estado de cada protocolo da lista, verificando os seguintes estados do protocolo e 
	 *  atribuindo mensagens de erro ao TramiteDto daquele protocolo quando for o caso:
	 *  excluído, substituído, anexado, apensado, inventariado, já recebido e funcionário sem permissão na unidade.
	 * 
	 * @return this
	 */
	public ValidaRecebimentoProtocoloBuilder validarEstadoDoProtocolo() {
		
		for (TramiteDto dto : listTramiteDto) {
			
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloExcluido(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setmensagemParaProtocoloSubstituido(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloAnexado(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloApensado(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloInventariado(dto));
			dto.getMensagensDoTramite().add(setMensagemParaProtocoloTramiteJaRecebido(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemPermissaoFuncionarioNaUnidadeProtocolo(dto));
			dto.getMensagensDoTramite().add(validateGenericTramite.setMensagemParaProtocoloGerenciadoPeloSiacol(dto));

		}
		
		return this;
	}
	
	/** Verifica se o protocolo já está recebido
	 *  e define mensagem de erro se for o caso 
	 * 
	 * @param dto
	 * @return mensagem
	 */
	public String setMensagemParaProtocoloTramiteJaRecebido(TramiteDto dto) {
		String mensagem = "";
		
		if(dto.getUltimoMovimento().getDataRecebimento() != null) {
			
			dto.setPossuiErros(true);
			mensagem = messages.protocoloTramiteJaRecebido(dto.getNumeroProtocolo());	
		}
		return mensagem;
	}
	
	/** Se o protocolo for eletrônico, valida as seguintes condições:
	 *  localizaçãoEprocesso (verifica se movimento está divergente do corporativo), 
	 *  funcionarioTramiteEprocesso (verifica se o funcionário está alocado naquela unidade), 
	 *  unidadePadraoTramiteEprocesso (verifica se unidade padrão do funcionário no docflow difere do corporativo), 
	 *  protocoloRecebidoEprocesso, 
	 *  EprocessoBloqueado
	 *  
	 * @return this
	 */
	public ValidaRecebimentoProtocoloBuilder validarProtocoloEletronico() {
		
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
				dto.getMensagensDoTramite().add(setMensagemProtocoloRecebidoEprocesso(dto));
				dto.getMensagensDoTramite().add(setEProcessoBloqueado(dto));
			}
		}
		
		return this;
	}
	
	/** Verifica se o protocolo está recebido no docflow
	 *  e define mensagem de erro se for o caso.
	 * 
	 * @param dto
	 * @return mensagem
	 */
	public String setMensagemProtocoloRecebidoEprocesso(TramiteDto dto) {
		String mensagem = "";
		
		genericDocflowDto = new DocflowGenericDto();
		genericDocflowDto.setNumeroProtocolo(dto.getNumeroProtocolo().toString());
		
		boolean protocoloEstaRecebido = docflowService.protocoloEstaRecebidoDocflow(genericDocflowDto);
		
		if(protocoloEstaRecebido) {
			dto.setPossuiErros(true);
			mensagem = messages.tramiteRecebidoDocflow(dto.getNumeroProtocolo());
		}
		
		return mensagem;
	}
	
	/** Verifica se o Eprocesso está bloqueado
	 *  e define mensagem de erro se for o caso.
	 *  
	 * @param dto
	 * @return mensagem
	 */
	public String setEProcessoBloqueado(TramiteDto dto) {
		
		String mensagem = "";
		
		Eprocesso eprocesso = eprocessoDao.getBy(1L);
		
		if(!eprocesso.estaLiberado() || !eprocesso.podeReceber()) {
			dto.setPossuiErros(true);
			mensagem = messages.eprocessoNaoLiberadoParaTramiteRecebimento();
		}
		
		return mensagem;
	}
	
	/** Remove as mensagens vazias do protocolo
	 * 
	 * @return this
	 */
	public ValidaRecebimentoProtocoloBuilder validarMensagensProtocolo() {
		
		listTramiteDto.forEach(p -> formatMensagensValidacao.accept(p.getMensagensDoTramite()));
		return this;
	}
	
	/** Retorna lista de TramiteDto define nesta classe
	 * @return lista de TramiteDto
	 */
	public List<TramiteDto> build() {
	
		return listTramiteDto;
	}

}
