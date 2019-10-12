package br.org.crea.commons.builder.protocolo.validaterules;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.TipoDocumentoDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.interfaceutil.EntityCreaExistence;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.financeiro.FinDivida;

public class ValidaCadastroProtocoloBuilder {
	
	@Inject AssuntoDao assuntoDao;
	
	@Inject PessoaDao pessoaDao;
	
	@Inject DepartamentoDao departamentoDao;
	
	@Inject TipoDocumentoDao tipoDocumentoDao;
	
	@Inject ProtocoloDao protocoloDao;
	
	@Inject FinDividaDao finDividaDao;
	
	@Inject EntityCreaExistence validExist;
	
	@Inject HelperMessages message;
	
	private ProtocoloDto protocoloDto;

	/**
	 * Método que executa as validações das informações para cadastrar um protocolo
	 * Valida os dados do protocolo
	 * Validar as informações do documento que será cadastrado no Docflow.
	 * Se houver alguma mensagem de validação, o atributo possuiErros no ProtocoloDto receberá true.
	 * @param protocoloDto
	 * @return this
	 */
	public ValidaCadastroProtocoloBuilder validarCadastro(ProtocoloDto protocoloDto){
		
		this.protocoloDto = protocoloDto;
		
		validarDadosObrigatoriosProtocolo().validarDividasNaoPagasProfissional().validarDividasNaoPagasEmpresa();
		protocoloDto.setPossuiErros(!protocoloDto.getMensagens().isEmpty());
		
		return this;
	}

	/**
	 * Valida os dados obrigatórios para cadastro do protocolo:
	 * Assunto, interessado, departamento e processo.
	 * @return ValidateCadastroProtocoloBuilder
	 */
	public ValidaCadastroProtocoloBuilder validarDadosObrigatoriosProtocolo() {
		
		validAssuntoNullable().validPessoaNullable().validInteressadoNullable().validDepartamentoNullable();

		if (protocoloDto.getNumeroProcesso() != null && protocoloDto.getInteressado() != null) {
			if (protocoloDto.getNumeroProcesso() != 0L && !protocoloDao.hasProcessoInteressado(protocoloDto.getNumeroProcesso(), protocoloDto.getInteressado().getId())) {
				protocoloDto.getMensagens().add(message.processoInexistenteOuNaoEhdoInteressado());
			}
		}
		
		return this;
	}

	public ValidaCadastroProtocoloBuilder validAssuntoNullable() {
		Optional.ofNullable(protocoloDto.getAssunto()).ifPresent(assunto -> {
			
			if (assunto.getId() != null) {
				
				if( !validExist.apply(assunto.getId(), Assunto.class.getSimpleName())){
					protocoloDto.getMensagens().add(message.assuntoProtocoloNotExist(assunto.getId()));
				}
				
			} else {
				protocoloDto.getMensagens().add(message.assuntoProtocoloNaoInformado());
			}
			
		});
		return this;
	}
	
	public ValidaCadastroProtocoloBuilder validAssuntoTemTipoProcesso() {
		Assunto assunto = assuntoDao.getAssuntoBy(protocoloDto.getAssunto().getId());
		
		Optional.ofNullable(assunto.getBloco().getTipoProcesso()).ifPresent(tipoProcesso -> {

			if (tipoProcesso == null) {

				protocoloDto.getMensagens().add(message.assuntoNaoTemTipoProcesso(assunto.getDescricao()));
			
			}

		});
		return this;
	}

	public ValidaCadastroProtocoloBuilder validPessoaNullable() {
		Optional.ofNullable(protocoloDto.getPessoa()).ifPresent(pessoa -> {
			
			if (pessoa.getId() != null) {
				
				if( !validExist.apply(pessoa.getId(), Pessoa.class.getSimpleName())){
					protocoloDto.getMensagens().add(message.pessoaProtocoloNotExist(pessoa.getId()));
				}
				
			} else {
				protocoloDto.getMensagens().add(message.pessoaProtocoloNaoInformada());
			}
			
		});
		return this;
	}
	
	public ValidaCadastroProtocoloBuilder validInteressadoNullable() {
		Optional.ofNullable(protocoloDto.getInteressado()).ifPresent(interessado -> {
			
			if (interessado.getId() != null) {
				
				if( !validExist.apply(interessado.getId(), Pessoa.class.getSimpleName())){
					protocoloDto.getMensagens().add(message.interessadoProtocoloNotExist(interessado.getId()));
				}
				
			} else {
				protocoloDto.getMensagens().add(message.interessadoProtocoloNaoInformada());
			}
		});
		return this;
	}
	
	public ValidaCadastroProtocoloBuilder validDepartamentoNullable() {
		if(protocoloDto.getIdDepartamentoOrigem() != null){
			if( !validExist.apply(protocoloDto.getIdDepartamentoOrigem(), Departamento.class.getSimpleName())) {
				protocoloDto.getMensagens().add(message.departamentoNotExist(protocoloDto.getIdDepartamentoOrigem()));
	
			} 
		}else {
			protocoloDto.getMensagens().add(message.departamentoProtocoloNaoInformada());
		}
				
		return this;
	}

	/**
	 * Verifica se existem dividas, caso o assunto do protocolo do profissional for uma das opções abaixo:
	 * REATIVACAO DE REGISTRO
	 * REABILITACAO DE REGISTRO - ART. 64/5194
	 * REATIVAÇÃO DE REGISTRO PROCESSO TRANSFERIDO PARA O CAU/RJ
	 * @return ValidateCadastroProtocoloBuilder
	 */
	public ValidaCadastroProtocoloBuilder validarDividasNaoPagasProfissional(){
		if (protocoloDto.getAssunto().getId().equals(new Long(1016)) || 
				protocoloDto.getAssunto().getId().equals(new Long(1017)) ||
				protocoloDto.getAssunto().getId().equals(new Long(1233))) {

			List<FinDivida> listaDividasNaoPagas = finDividaDao.buscaTodasDivNaoQuitadasByPessoaParaReabilitacao(protocoloDto.getInteressado().getId());
			if (!listaDividasNaoPagas.isEmpty()) {
				protocoloDto.getMensagens().add(message.profissionalQuantidadeDividaNaoPaga(listaDividasNaoPagas));
			}
		}
		return this;
	}
	
	/**
	 * Verifica se existem dividas, caso o assunto do protocolo da empresa for uma das opções abaixo:
	 * REATIVACAO DE REGISTRO
	 * REABILITACAO DE REGISTRO - ART 64/5194
	 * REABILITAÇÃO DE REGISTRO - PROC. TRANSFERIDO PARA CAU-RJ
	 * @return ValidateCadastroProtocoloBuilder
	 */
	public ValidaCadastroProtocoloBuilder validarDividasNaoPagasEmpresa(){
		if (protocoloDto.getAssunto().getId().equals(new Long(2014)) ||
				protocoloDto.getAssunto().getId().equals(new Long(2015)) || 
				protocoloDto.getAssunto().getId().equals(new Long(2242))) {

			List<FinDivida> listaDividasNaoPagas = finDividaDao.buscaTodasDivNaoQuitadasByPessoaParaReabilitacao(protocoloDto.getInteressado().getId());

			if (!listaDividasNaoPagas.isEmpty()) {
				protocoloDto.getMensagens().add(message.empresaQuantidadeDividaNaoPaga(listaDividasNaoPagas));
			}
		}
		
		return this;
	}
	
	/**
	 * Retorna ProtocoloDto definida nesta classe.
	 * @return ProtocoloDto
	 */
	public ProtocoloDto build(){
		return protocoloDto;
	}
}
