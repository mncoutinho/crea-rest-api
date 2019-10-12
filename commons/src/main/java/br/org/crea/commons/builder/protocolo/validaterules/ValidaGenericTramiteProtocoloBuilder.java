package br.org.crea.commons.builder.protocolo.validaterules;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.dto.UsuarioDocflowDto;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;

public class ValidaGenericTramiteProtocoloBuilder {

	@Inject
	HelperMessages messages;

	@Inject
	ProtocoloDao protocoloDao;
	
	@Inject
	ProtocoloSiacolDao protocoloSiacolDao;

	@Inject
	DocflowService docflowService;

	@Inject
	FuncionarioDao funcionarioDao;

	@Inject
	DepartamentoDao departamentoDao;

	private DocflowGenericDto genericDocflowDto;

	public String setMensagemParaProtocoloExcluido(TramiteDto dto) {
		String mensagem = "";

		if (dto.isEstaExcluido()) {

			dto.setPossuiErros(true);
			mensagem = dto.isEstaExcluido() ? messages.protocoloExcluido(dto.getNumeroProtocolo()) : "";
		}
		return mensagem;
	}

	public String setmensagemParaProtocoloSubstituido(TramiteDto dto) {
		String mensagem = "";

		if (dto.isEstaSubstituido()) {

			dto.setPossuiErros(true);
			mensagem = dto.isEstaSubstituido() ? messages.protocoloSubstituido(dto.getNumeroProtocolo()) : "";
		}
		return mensagem;
	}

	public String setMensagemParaProtocoloAnexado(TramiteDto dto) {
		String mensagem = "";

		if (dto.isEstaAnexado()) {

			dto.setPossuiErros(true);
			mensagem = dto.isEstaAnexado() ? messages.protocoloAnexado(dto.getNumeroProtocolo()) : "";
		}
		return mensagem;

	}

	public String setMensagemParaProtocoloApensado(TramiteDto dto) {
		String mensagem = "";

		if (dto.isEstaApensado()) {

			dto.setPossuiErros(true);
			mensagem = dto.isEstaApensado() ? messages.protocoloApensado(dto.getNumeroProtocolo()) : "";
		}
		return mensagem;
	}

	public String setMensagemParaProtocoloInventariado(TramiteDto dto) {
		String mensagem = "";

		if (protocoloDao.protocoloEstaMarcadoParaInventario(dto.getNumeroProtocolo())) {

			dto.setPossuiErros(true);
			mensagem = messages.protocoloInventariado(dto.getNumeroProtocolo());
		}
		return mensagem;
	}

	public String setMensagemPermissaoFuncionarioNaUnidadeProtocolo(TramiteDto dto) {
		String mensagem = "";

		if (!funcionarioPossuiPermissaoNaUnidadeDoProtocolo(dto)) {

			dto.setPossuiErros(true);
			mensagem = messages.permissaoFuncionarioUnidadeProtocolo(dto.getNumeroProtocolo());
		}

		return mensagem;
	}

	public String setMensagemLocalizacaoEprocesso(TramiteDto dto) {
		String mensagem = "";
		genericDocflowDto = new DocflowGenericDto();
		genericDocflowDto.setNumeroProtocolo(dto.getNumeroProtocolo().toString());

		boolean movimentoEstaDivergente = docflowService.movimentoProtocoloPossuiDivergencia(genericDocflowDto, dto.getUltimoMovimento().getIdDepartamentoDestino());

		if (movimentoEstaDivergente) {
			dto.setPossuiErros(true);
			mensagem = messages.localizacaoEprocesso(dto.getNumeroProtocolo());
		}

		return mensagem;
	}

	public String setMensagemUnidadePadraoTramiteEprocesso(TramiteDto dto) {
		String mensagem = "";

		genericDocflowDto.setMatricula(String.valueOf(dto.getFuncionarioTramite().getMatricula()));

		UsuarioDocflowDto usuarioDocflow = docflowService.consultarUsuario(genericDocflowDto);

		if (!usuarioDocflow.getCodigoUnidadePadrao().equals(dto.getFuncionarioTramite().getIdDepartamento().toString())) {
			dto.setPossuiErros(true);
			mensagem = messages.unidadePadraoFuncionarioEprocesso();
		}

		return mensagem;
	}

	public boolean funcionarioDoTramiteEhConselheiro(Long idFuncionario) {
		return idFuncionario.equals(new Long(99990L)) ? true : false;
	}

	public boolean funcionarioPossuiPermissaoNaUnidadeDoProtocolo(TramiteDto dto) {

		PesquisaGenericDto pesquisa = new PesquisaGenericDto();
		pesquisa.setIdDepartamento(dto.getUltimoMovimento().getIdDepartamentoDestino());
		pesquisa.setIdFuncionario(dto.getFuncionarioTramite().getId());

		return !funcionarioDoTramiteEhConselheiro(dto.getFuncionarioTramite().getId()) && funcionarioDao.funcionarioPossuiPermissaoTramiteNaUnidade(pesquisa) ? true : false;

	}

	public String setMensagemFuncionarioTramiteEprocesso(TramiteDto dto) {
		String mensagem = "";
		genericDocflowDto.setNumeroProtocolo(dto.getNumeroProtocolo().toString());
		genericDocflowDto.setMatricula(String.valueOf(dto.getFuncionarioTramite().getMatricula()));

		boolean usuarioEstaNaMesmaUnidadeDoProtocolo = docflowService.usuarioEstaNaMesmaUnidadeProtocoloTramitado(genericDocflowDto);

		if (!usuarioEstaNaMesmaUnidadeDoProtocolo) {
			dto.setPossuiErros(true);
			mensagem = messages.funcionarioTramiteEprocesso(dto.getNumeroProtocolo());
		}

		return mensagem;
	}

	public String setMensagemParaProtocoloGerenciadoPeloSiacol(TramiteDto dto) {

		String mensagem = "";

		ModuloSistema moduloDepartamentoDoProtocolo = dto.getUltimoMovimento().getModuloDepartamentoDestino();

		if( moduloDepartamentoDoProtocolo != null ) {
			if (moduloDepartamentoDoProtocolo.equals(ModuloSistema.SIACOL) && 
					!funcionarioEhResponsavelProtocoloSiacol(dto.getNumeroProtocolo(), dto.getFuncionarioTramite())) {
				
				dto.setPossuiErros(true);
				mensagem = messages.protocoloGerenciadoPeloSiacol(dto.getNumeroProtocolo());
			}
		}

		return mensagem;

	}
	
	public boolean funcionarioEhResponsavelProtocoloSiacol(Long numeroProtocoloSiacol, FuncionarioDto funcionario) {
		
		ProtocoloSiacol protocoloSiacol = protocoloSiacolDao.getProtocoloBy(numeroProtocoloSiacol);
		
		if ( protocoloSiacol != null ) {
			
			Long idResponsavel = protocoloSiacol.getIdResponsavel();
			Long idCoordenadorDepartamentoProtocolo = protocoloSiacol.getDepartamento().getCoordenador() != null ? protocoloSiacol.getDepartamento().getCoordenador().getId() : 0; 
			Long idCoordenadorCoac = departamentoDao.getBy(new Long(230201)).getCoordenador() != null ? departamentoDao.getBy(new Long(230201)).getCoordenador().getId() : 0; 
			
			return	idResponsavel.equals(new Long(0)) ||
					funcionario.getIdPessoa().equals(idResponsavel) || 
					funcionario.getIdPessoa().equals(idCoordenadorDepartamentoProtocolo) ||
					funcionario.getIdPessoa().equals(idCoordenadorCoac) ? true : false;
		} else {
			
			return false;
		}
		
	}

}
