package br.org.crea.commons.builder.protocolo;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.dao.protocolo.SituacaoProtocoloDao;
import br.org.crea.commons.dao.siacol.HabilidadePessoaDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.dao.siacol.RlProtocoloResponsavelSiacolDao;
import br.org.crea.commons.factory.siacol.AuditaSiacolProtocoloFactory;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.SituacaoProtocolo;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.protocolo.dtos.MovimentoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.RlProtocoloResponsavelSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.empresa.EmpresaService;
import br.org.crea.commons.util.StringUtil;

public class ImportarProtocoloSiacolBuilder {

	@Inject
	HttpClientGoApi httpGoApi;

	@Inject
	ProtocoloSiacolDao protocoloSiacolDao;

	@Inject
	AuditaSiacolProtocoloFactory auditaSiacolProtocolo;

	@Inject
	ProtocoloDao protocoloDao;

	@Inject
	HabilidadePessoaDao habilidadeDao;

	@Inject
	InteressadoDao interessadoDao;

	@Inject
	PessoaDao pessoaDao;

	@Inject
	RlProtocoloResponsavelSiacolDao rlProtocoloResponsavelDao;
	
	@Inject
	AuditaSiacolProtocoloFactory auditaFactory;

	@Inject
	EmpresaService empresaService;

	@Inject
	HabilidadePessoaDao habilidadePessoaDao;

	@Inject
	DepartamentoDao departamentoDao;

	@Inject
	SituacaoProtocoloDao situacaoProtocoloDao;
	

	private ProtocoloSiacol protocoloSiacol;

	private Protocolo protocoloDoSistema;

	private Long numeroProtocolo;

	private Long idDepartamentoOrigem;
	
	private Long idDepartamentoDestino;

	public ImportarProtocoloSiacolBuilder protocolo(Long numeroProtocolo) {

		this.numeroProtocolo = numeroProtocolo;
		protocoloSiacol = protocoloSiacolDao.getProtocoloBy(numeroProtocolo) != null ? 
				protocoloSiacolDao.getProtocoloBy(numeroProtocolo) : new ProtocoloSiacol() ;

		protocoloDoSistema = protocoloDao.getProtocoloBy(numeroProtocolo);
		return this;
	}


	public ImportarProtocoloSiacolBuilder origem(MovimentoProtocoloDto movimentoProtocoloDto) {
		if (movimentoProtocoloDto != null) {
			this.idDepartamentoOrigem = movimentoProtocoloDto.getIdDepartamentoDestino();
		}
		
		return this;
	}
	
	public ImportarProtocoloSiacolBuilder destino(Long idDepartamentoDestino) {

		this.idDepartamentoDestino = idDepartamentoDestino;
		return this;
	}

	public ImportarProtocoloSiacolBuilder responsavel(UserFrontDto usuario) {

		if (protocoloJaEstaNoSiacol(this.numeroProtocolo) && this.protocoloSiacol.temStatus()
				&& this.protocoloSiacol.getStatus().getTipo() == "AD_REFERENDUM_ASSINADO") {

				protocoloSiacol.setStatus(StatusProtocoloSiacol.A_RECEBER_AD_REFERENDUM);
				Long idResponsavel = habilidadePessoaDao.verificaAtivo(this.protocoloSiacol.getUltimoAnalista())
						? this.protocoloSiacol.getUltimoAnalista()
						: departamentoDao.getBy(new Long(230201)).getCoordenador().getId();

				protocoloSiacol.setIdResponsavel(idResponsavel);
				protocoloSiacol.setNomeResponsavel(pessoaDao.getBy(idResponsavel).getNome());
				return this;

		}

		if (protocoloJaEstaNoSiacol(this.numeroProtocolo) && this.protocoloSiacol.temStatus()
				&& this.protocoloSiacol.getStatus().getTipo() == "AGUARDANDO_PROVISORIO") {

			if (empresaService.verificaProvisorio(this.protocoloSiacol.getIdInteressado())) {
				protocoloSiacol.setStatus(StatusProtocoloSiacol.A_PAUTAR_PROVISORIO);
				protocoloSiacol.setProvisorio(true);
				protocoloSiacol.setIdResponsavel(new Long(0));
				protocoloSiacol.setNomeResponsavel("SEM RESPONSAVEL");
				return this;
			}
		}

//		if (protocoloJaEstaNoSiacol(this.numeroProtocolo) && importParaMesmoDepartamento(idDepartamentoDestino)) {
//			// String nomeUltimoAnalista =
//			// interessadoDao.buscaInteressadoBy(this.protocoloSiacol.getUltimoAnalista()).getNome();
//
//			Long idResponsavel = habilidadePessoaDao.verificaAtivo(this.protocoloSiacol.getUltimoAnalista())
//					? this.protocoloSiacol.getUltimoAnalista()
//					: departamentoDao.getBy(new Long(230201)).getCoordenador().getId();
//			protocoloSiacol.setIdResponsavel(idResponsavel);
//			protocoloSiacol.setNomeResponsavel(pessoaDao.getBy(idResponsavel).getNome());
//
//		} else {

			if (rlProtocoloResponsavelDao.protocoloJaEstaVinculado(protocoloDoSistema.getNumeroProtocolo())) {
				DomainGenericDto responsavelDto = buscaResponsavelVinculadoParaDistribuicao();

				protocoloSiacol.setIdResponsavel(responsavelDto.getId());
				protocoloSiacol.setNomeResponsavel(responsavelDto.getNome());
				rlProtocoloResponsavelDao.atualizaVinculoProtocoloNaImportacao(protocoloDoSistema.getNumeroProtocolo());

			} else if (protocoloSiacol.getIdResponsavel() == null) {
				DomainGenericDto responsavelAleatorio = buscaResponsavelAleatorioParaDistribuicao(usuario);

				protocoloSiacol.setIdResponsavel(responsavelAleatorio.getId());
				protocoloSiacol.setNomeResponsavel(responsavelAleatorio.getNome());

			}
//		}
		return this;
	}

	public ImportarProtocoloSiacolBuilder insertOrUpdate(UserFrontDto usuario, TramiteDto dto) {

		try {
			String msgTramite = "";
			if (!dto.getMensagensDoTramite().isEmpty()) {
				msgTramite = dto.getMensagensDoTramite().get(0);
			}
			ProtocoloSiacol protocolo = populaInsertOrUpdateProtocoloSiacol(protocoloSiacol, protocoloDoSistema, usuario);

			msgTramite = msgTramite + " e distribuído para o " + this.protocoloSiacol.getNomeResponsavel();
			
			if (protocoloJaEstaNoSiacol(this.numeroProtocolo)) {
				protocoloSiacolDao.update(protocolo);
								
				auditaFactory.importProtocoloSiacol(protocolo, usuario, "U", msgTramite, this.numeroProtocolo, this.idDepartamentoOrigem, this.idDepartamentoDestino, this.protocoloSiacol.getIdResponsavel(), this.protocoloSiacol.getNomeResponsavel());

				auditaSiacolProtocolo.alteraStatus(usuario, protocolo, populaUsuarioResponsavel());
			} else {
				protocoloSiacolDao.create(protocolo);
				
				auditaFactory.importProtocoloSiacol(protocolo, usuario, "C", msgTramite, this.numeroProtocolo, this.idDepartamentoOrigem, this.idDepartamentoDestino, this.protocoloSiacol.getIdResponsavel(), this.protocoloSiacol.getNomeResponsavel());
				
				auditaSiacolProtocolo.alteraStatus(usuario, protocolo, populaUsuarioResponsavel());
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("ImportProtocoloSiacolBuilder || insertOrUpdate",
					StringUtil.convertObjectToJson(protocoloSiacol), e);
		}

		return this;
	}

	public boolean protocoloJaEstaNoSiacol(Long numeroProtocolo) {
		ProtocoloSiacol protocolo = protocoloSiacolDao.getProtocoloBy(numeroProtocolo);

		if (protocolo != null) {

			protocoloSiacol = protocolo;
			return true;

		} else {
			return false;
		}
	}

	public boolean importParaMesmoDepartamento(Long idDepartamentoDestino) {

		return this.protocoloSiacol.getDepartamento().getId().equals(idDepartamentoDestino) ? true : false;
	}

	public DomainGenericDto buscaResponsavelAleatorioParaDistribuicao(UserFrontDto usuario) {

		GenericSiacolDto genericDto = new GenericSiacolDto();
		genericDto.setIdDepartamento(idDepartamentoDestino);
		genericDto.setIdAssunto(protocoloDoSistema.getAssunto().getId());
		genericDto.setDistribuicaoParaConselheiro(false);
		genericDto.setNumeroProtocolo(protocoloDoSistema.getNumeroProtocolo());

		DomainGenericDto responsavelDto = new DomainGenericDto();
		
		responsavelDto.setId(habilidadeDao.getResponsavelDistribuicao(genericDto, usuario).getId());

		String nomeResponsavel = interessadoDao.buscaInteressadoBy(responsavelDto.getId()).getNome();
		responsavelDto.setNome(nomeResponsavel);

		GenericSiacolDto bloqueioResponsavelDto = new GenericSiacolDto();
		bloqueioResponsavelDto.setIdResponsavelAtual(responsavelDto.getId());
		bloqueioResponsavelDto.setIdDepartamento(idDepartamentoDestino);
		bloqueioResponsavelDto.setIdAssunto(protocoloDoSistema.getAssunto().getId());
		bloqueioResponsavelDto.setDistribuicaoParaConselheiro(false);
		habilidadeDao.bloquearResponsavelParaDistribuicao(bloqueioResponsavelDto);

		return responsavelDto;
	}

	public DomainGenericDto buscaResponsavelVinculadoParaDistribuicao() {

		DomainGenericDto responsavelDto = new DomainGenericDto();

		RlProtocoloResponsavelSiacol rlProtocoloResponsavel = rlProtocoloResponsavelDao
				.getProtocoloVinculadoDoResponsavel(protocoloDoSistema.getNumeroProtocolo());
		responsavelDto.setId(rlProtocoloResponsavel.getResponsavel().getId());

		String nomeResponsavel = interessadoDao.buscaInteressadoBy(rlProtocoloResponsavel.getResponsavel().getId())
				.getNome();
		responsavelDto.setNome(nomeResponsavel);
		return responsavelDto;
	}

	public ProtocoloSiacol populaInsertOrUpdateProtocoloSiacol(ProtocoloSiacol protocoloSiacol, Protocolo protocoloDoSistema, UserFrontDto usuario) {

		String nomeInteressado = interessadoDao.buscaInteressadoBy(protocoloDoSistema.getPessoa()).getNomeRazaoSocial();

		protocoloSiacol.setNumeroProtocolo(protocoloDoSistema.getNumeroProtocolo());
		protocoloSiacol.setNumeroProcesso(protocoloDoSistema.getNumeroProcesso());
		protocoloSiacol.setDataCadastro(protocoloDoSistema.getDataEmissao());
		protocoloSiacol.setDataRecebimento(null);
		protocoloSiacol.setIdInteressado(protocoloDoSistema.getPessoa().getId());
		protocoloSiacol.setNomeInteressado(nomeInteressado);
		protocoloSiacol.setIdAssuntoCorportativo(protocoloDoSistema.getAssunto().getId());
		protocoloSiacol.setDescricaoAssuntoCorporativo(protocoloDoSistema.getAssunto().getDescricao());
		protocoloSiacol.setDescricaoTipoAssuntoCorporativo(protocoloDoSistema.getAssunto().getTipoAssunto().name());
		protocoloSiacol.setSituacao(protocoloDoSistema.getUltimoMovimento().getSituacao());
		protocoloSiacol.setDepartamento(protocoloDoSistema.getUltimoMovimento().getDepartamentoDestino());
		protocoloSiacol.setObservacao(protocoloDoSistema.getObservacao());
		protocoloSiacol.setDataSiacol(new Date());
		protocoloSiacol.setAtivo(true);
		protocoloSiacol.setRecebido(false);
		protocoloSiacol.setDataRecebimento(null);
		protocoloSiacol.setHomologacaoPF(false);
		protocoloSiacol.setProvisorio(protocoloSiacol.getProvisorio() != null ? protocoloSiacol.getProvisorio() : false);
		protocoloSiacol.setAdReferendum(protocoloSiacol.getAdReferendum() != null ? protocoloSiacol.getAdReferendum() : false);
		
		SituacaoProtocolo situacaoProtocolo = situacaoProtocoloDao.getBy(new Long(2));
		protocoloSiacol.setSituacao(situacaoProtocolo);
		
		protocoloSiacol.setUltimoStatus(protocoloSiacol.getStatus());
		if (protocoloSiacol.temStatus() && (protocoloSiacol.getStatus().getTipo() == "AD_REFERENDUM_ASSINADO" || protocoloSiacol.getStatus().getTipo() == "A_RECEBER_AD_REFERENDUM")) {
			protocoloSiacol.setStatus(StatusProtocoloSiacol.A_RECEBER_AD_REFERENDUM);			
		}else if (protocoloSiacol.temStatus() && protocoloSiacol.getStatus().getTipo() == "AGUARDANDO_PROVISORIO") {
			protocoloSiacol.setStatus(StatusProtocoloSiacol.A_RECEBER_PROVISORIO);
		}else if (protocoloSiacol.temStatus() && protocoloSiacol.getStatus().getTipo() == "A_PAUTAR_PROVISORIO") {
			protocoloSiacol.setStatus(StatusProtocoloSiacol.A_PAUTAR_PROVISORIO);	
		}else if (!protocoloSiacol.temStatus()) {
			protocoloSiacol.setStatus(StatusProtocoloSiacol.AGUARDANDO_RECEBIMENTO);
		
		}else {
			protocoloSiacol.setStatus(StatusProtocoloSiacol.AGUARDANDO_RECEBIMENTO);
		}

		protocoloSiacol.setConselheiroDevolucao(null);
		protocoloSiacol.setUltimoAnalista(null);
		
		return protocoloSiacol;
	}

	private UserFrontDto populaUsuarioResponsavel() {
		UserFrontDto usuarioDestinatario = new UserFrontDto();
		usuarioDestinatario.setIdPessoa(this.protocoloSiacol.getIdResponsavel());
		usuarioDestinatario.setNome(this.protocoloSiacol.getNomeResponsavel());
		usuarioDestinatario.setPerfil("ANALISTA");
		
		return usuarioDestinatario;
	}

}
