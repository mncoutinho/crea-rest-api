package br.org.crea.commons.factory.siacol;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.dao.siacol.ProtocoloSiacolDao;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.SiacolProtocoloExigenciaDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.StringUtil;

public class AuditaSiacolProtocoloFactory {

	@Inject private AuditoriaService service;
	
	@Inject private HelperMessages messages;
	
	@Inject private PessoaDao pessoaDao;
	
	@Inject private AssuntoSiacolDao assuntoSiacolDao;
	
	@Inject private ProtocoloSiacolDao protocoloSiacolDao;
	
	@Inject private HttpClientGoApi httpGoApi;
	
	public void auditaDistribuicao(GenericSiacolDto dto, UserFrontDto usuario, ProtocoloSiacol protocolo, Long referencia) {
		
		try {
			String perfilRemetente = "";
			String perfilDestinatario = "";

			if (dto.getIdResponsavelNovo() != null) {
				perfilDestinatario = pessoaDao.buscaPerfilPor(dto.getIdResponsavelNovo());
			}
			
			if (dto.getIdResponsavelAtual() != null) {
				perfilRemetente = pessoaDao.buscaPerfilPor(dto.getIdResponsavelAtual());
			}
			
			TipoEventoAuditoria evento = null;
			
			if (!usuario.getIdPessoa().equals(dto.getIdResponsavelAtual())) {
				evento = dto.getEvento(); //FIXME front deverá informar o evento
				if (!dto.temEvento()) evento = TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO;
			} else {
				evento = TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO;
			}
			
			  service
			  .usuario(usuario)
		      .modulo(ModuloSistema.SIACOL)
		      .dataCriacao(new Date())
		      .numero(String.valueOf(protocolo.getNumeroProtocolo()))
		      .numeroReferencia(populaReferencia(protocolo))
//		      .numeroReferencia(referencia != null ? String.valueOf(referencia) : null)
		      .dtoNovo(StringUtil.convertObjectToJson(protocolo))
		      .dtoClass(ProtocoloSiacol.class.getSimpleName())
			  .tipoEvento(evento)
			  .departamentoDestino(dto.getIdDepartamento())
			  .departamentoOrigem(protocolo.getDepartamento().getId())
			  .responsavelRemetente(dto.getIdResponsavelAtual(), dto.getNomeResponsavelAtual(), perfilRemetente)
			  .responsavelDestino(dto.getIdResponsavelNovo(), dto.getNomeResponsavelNovo(), perfilDestinatario)
			  .textoAuditoria(messages.distribuicao(dto, usuario, protocolo, perfilRemetente, perfilDestinatario))
			  .statusNovo(dto.temStatus() ? dto.getStatus().getTipo() : null)
			  .create();
			  
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || auditaDistribuicao", StringUtil.convertObjectToJson(dto + " - " + usuario + " - " + protocolo), e);
		}
	}
	
	public void auditaDistribuicaoAnexo(GenericSiacolDto dto, UserFrontDto usuario, ProtocoloSiacol protocolo, Long referencia) {
		
		try {
			String perfilRemetente = "";
			String perfilDestinatario = "";

			if (dto.getIdResponsavelNovo() != null) {
				perfilDestinatario = pessoaDao.buscaPerfilPor(dto.getIdResponsavelNovo());
			}
			
			if (dto.getIdResponsavelAtual() != null) {
				perfilRemetente = pessoaDao.buscaPerfilPor(dto.getIdResponsavelAtual());
			}
			
			TipoEventoAuditoria evento = null;
			
			if (usuario.getIdPessoa().equals(dto.getIdResponsavelAtual())) {
				evento = dto.getEvento(); //FIXME front deverá informar o evento
				if (!dto.temEvento()) evento = TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO_ANEXO;
			} else {
				evento = TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO_ANEXO;
			}
			
			  service
			  .usuario(usuario)
		      .modulo(ModuloSistema.SIACOL)
		      .dataCriacao(new Date())
		      .numero(String.valueOf(protocolo.getNumeroProtocolo()))
		      .numeroReferencia(populaReferencia(protocolo))
//		      .numeroReferencia(referencia != null ? String.valueOf(referencia) : null)
		      .dtoNovo(StringUtil.convertObjectToJson(protocolo))
		      .dtoClass(ProtocoloSiacol.class.getSimpleName())
			  .tipoEvento(evento)
			  .departamentoDestino(dto.getIdDepartamento())
			  .departamentoOrigem(protocolo.getDepartamento().getId())
			  .responsavelRemetente(dto.getIdResponsavelAtual(), dto.getNomeResponsavelAtual(), perfilRemetente)
			  .responsavelDestino(dto.getIdResponsavelNovo(), dto.getNomeResponsavelNovo(), perfilDestinatario)
			  .textoAuditoria(messages.distribuicao(dto, usuario, protocolo, perfilRemetente, perfilDestinatario))
			  .statusNovo(dto.temStatus() ? dto.getStatus().getTipo() : null)
			  .create();
			  
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || auditaDistribuicaoAnexo", StringUtil.convertObjectToJson(dto + " - " + usuario + " - " + protocolo), e);
		}
		
	}

	public void auditaClassificar(UserFrontDto usuario, ProtocoloSiacol protocolo) {
		try {
			
			service
			  .usuario(usuario)
		      .modulo(ModuloSistema.SIACOL)
		      .dataCriacao(new Date())
			  .numero(String.valueOf(protocolo.getNumeroProtocolo()))
			  .numeroReferencia(populaReferencia(protocolo))
			  .textoAuditoria(messages.alterarAssuntoSiacolProtocolo(protocolo.getNumeroProtocolo(), protocolo.temAssunto() ? assuntoSiacolDao.getBy(protocolo.getAssuntoSiacol().getId()).getNome() : ""))
			  .tipoEvento(TipoEventoAuditoria.SIACOL_CLASSIFICAR_PROTOCOLO)
		      .dtoNovo(StringUtil.convertObjectToJson(protocolo))
		      .dtoClass(ProtocoloSiacol.class.getSimpleName())
			  .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || auditaClassificar", StringUtil.convertObjectToJson(usuario + " - " + protocolo), e);
		}
	}
	
	public void auditaClassificacaoFinal(UserFrontDto usuario, ProtocoloSiacol protocolo) {
		try {
			
			service
			  .usuario(usuario)
		      .modulo(ModuloSistema.SIACOL)
		      .dataCriacao(new Date())
			  .numero(String.valueOf(protocolo.getNumeroProtocolo()))
			  .numeroReferencia(populaReferencia(protocolo))
			  .textoAuditoria(messages.alterarAssuntoSiacolProtocolo(protocolo.getNumeroProtocolo(), protocolo.temAssunto() ? protocolo.getAssuntoSiacol().getNome() : ""))
			  .tipoEvento(TipoEventoAuditoria.SIACOL_CLASSIFICAR_PROTOCOLO)
		      .dtoNovo(StringUtil.convertObjectToJson(protocolo))
		      .dtoClass(ProtocoloSiacol.class.getSimpleName())
			  .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || auditaClassificacaoFinal", StringUtil.convertObjectToJson(usuario + " - " + protocolo), e);
		}
		
	}

	public void alteraStatus(UserFrontDto usuario, ProtocoloSiacol protocoloAlterado, UserFrontDto usuarioDestino) {
		try {
			if (usuario == null) {
				usuario = new UserFrontDto();
				usuario.setIdPessoa(99990L);
				usuario.setNome("SISTEMA");
				usuario.setPerfil("SISTEMA");
			}
			
			if (usuarioDestino == null) usuarioDestino = usuario;
				
					
			service
			  .usuario(usuario)
			  .responsavelRemetente(usuario.getIdPessoa(), usuario.getNome(), usuario.getPerfilString())
			  .responsavelDestino(usuarioDestino.getIdPessoa(), usuarioDestino.getNome(), pessoaDao.buscaPerfilPor(usuarioDestino.getIdPessoa()))
		      .modulo(ModuloSistema.SIACOL)
			  .numero(String.valueOf(protocoloAlterado.getNumeroProtocolo()))
			  .numeroReferencia(populaReferencia(protocoloAlterado))
		      .statusAntigo(protocoloAlterado.temUltimoStatus() ? protocoloAlterado.getUltimoStatus().getDescricao() : null)
		      .statusNovo(protocoloAlterado.temStatus() ? protocoloAlterado.getStatus().getTipo() : null)
		      .dtoNovo(StringUtil.convertObjectToJson(protocoloAlterado))
		      .dtoClass(ProtocoloSiacol.class.getSimpleName())
		      .dataCriacao(new Date())
			  .tipoEvento(TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO)
			  .textoAuditoria(messages.alterarStatusProtocoloSiacol(protocoloAlterado))
			  .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || alteraStatus", StringUtil.convertObjectToJson(usuario + " - " + protocoloAlterado), e);
		}
		
	}
	
	private String populaReferencia(ProtocoloSiacol protocolo) {
		String numeroReferencia = null;

		boolean ehProtocoloPaiDeUmAnexo = protocoloSiacolDao.temAnexoNoSiacol(protocolo.getId());
		if (ehProtocoloPaiDeUmAnexo) {
			numeroReferencia = String.valueOf(protocolo.getNumeroProtocolo()); 
		} else {
			Long numeroProtocoloPai = protocoloSiacolDao.getNumeroProtocoloPaiNoSiacol(protocolo.getNumeroProtocolo());
			numeroReferencia = numeroProtocoloPai != null ? String.valueOf(numeroProtocoloPai) : null;
		}

		return numeroReferencia;
	}

	public void justificativaDevolucao(UserFrontDto usuario, ProtocoloSiacol protocolo) {
		
		try {
			
			service
			.usuario(usuario)
			.modulo(ModuloSistema.SIACOL)
			.numero(String.valueOf(protocolo.getNumeroProtocolo()))
			.numeroReferencia(populaReferencia(protocolo))
			.dtoNovo(StringUtil.convertObjectToJson(protocolo))
			.dtoClass(ProtocoloSiacol.class.getSimpleName())
			.dataCriacao(new Date())
			.tipoEvento(TipoEventoAuditoria.ALTERA_SITUACAO_PROTOCOLO)
			.create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || justificativaDevolucao", StringUtil.convertObjectToJson(usuario + " - " + protocolo), e);
		}
		
	}

	public void auditaRecebimento(GenericSiacolDto dto, ProtocoloSiacol protocolo, UserFrontDto usuario) {
		
		try {
			
			service
			  .usuario(usuario)
		      .modulo(ModuloSistema.SIACOL)
		      .dataCriacao(new Date())
			  .numero(String.valueOf(protocolo.getNumeroProtocolo()))
			  .numeroReferencia(populaReferencia(protocolo))
		      .departamentoDestino(protocolo.getDepartamento().getCodigo())
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dtoClass(ProtocoloSiacol.class.getSimpleName())
			  .tipoEvento(TipoEventoAuditoria.SIACOL_RECEBER_PROTOCOLO)
			  .textoAuditoria(messages.recebimentoSiacol(protocolo, usuario))
			  .statusAntigo(protocolo.temUltimoStatus() ? protocolo.getUltimoStatus().getTipo() : null)
			  .statusNovo(protocolo.temStatus() ? protocolo.getStatus().getTipo() : null)
			  .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || auditaRecebimento", StringUtil.convertObjectToJson(usuario + " - " + protocolo), e);
		}
		
	}

	public void importProtocoloSiacol(ProtocoloSiacol protocolo, UserFrontDto usuario, String acao, String msgTramite, Long numeroProtocolo, Long idDepartamentoOrigem, Long idDepartamentoDestino, Long idResponsavel, String nomeResponsavel) {
		
		try {
			
			service
			.usuario(usuario)
			.acao(acao)
			.modulo(ModuloSistema.SIACOL)
			.dataCriacao(new Date())
			.numero(String.valueOf(numeroProtocolo))
			.numeroReferencia(populaReferencia(protocolo))
			.departamentoOrigem(idDepartamentoOrigem)
			.departamentoDestino(idDepartamentoDestino)
			.responsavelDestino(idResponsavel, nomeResponsavel, "")
			.tipoEvento(TipoEventoAuditoria.TRAMITAR_PROTOCOLO)
			.dtoClass(ProtocoloSiacol.class.getSimpleName())
			.dtoNovo(StringUtil.convertObjectToJson(protocolo))
			.textoAuditoria(msgTramite)
			.create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || importProtocoloSiacol", StringUtil.convertObjectToJson(usuario + " - " + protocolo), e);
		}
		
	}

	public void pausa(ProtocoloSiacol protocolo, SiacolProtocoloExigenciaDto exigencia, UserFrontDto usuario) {
		
		try {
			
			service
			.usuario(usuario)
			.modulo(ModuloSistema.SIACOL)
			.dataCriacao(new Date())
			.numero(String.valueOf(protocolo.getNumeroProtocolo()))
			.numeroReferencia(populaReferencia(protocolo))
			.tipoEvento(TipoEventoAuditoria.SIACOL_PAUSAR_PROTOCOLO)
			.departamentoDestino(protocolo.getDepartamento().getId())
			.dtoClass(ProtocoloSiacol.class.getSimpleName())
			.dtoNovo(StringUtil.convertObjectToJson(protocolo))
			.textoAuditoria(messages.pausaSiacol(protocolo, usuario))
			.create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || pausa", StringUtil.convertObjectToJson(usuario + " - " + protocolo), e);
		}
		
	}

	public void retiraPausa(ProtocoloSiacol protocolo, SiacolProtocoloExigenciaDto exigencia, UserFrontDto usuario) {
		
		try {
			
			service
			.usuario(usuario)
			.modulo(ModuloSistema.SIACOL)
			.dataCriacao(new Date())
			.numero(String.valueOf(protocolo.getNumeroProtocolo()))
			.numeroReferencia(populaReferencia(protocolo))
			.tipoEvento(TipoEventoAuditoria.SIACOL_RETIRA_PAUSA_PROTOCOLO)
			.departamentoDestino(protocolo.getDepartamento().getId())
			.dtoClass(ProtocoloSiacol.class.getSimpleName())
			.dtoNovo(StringUtil.convertObjectToJson(protocolo))
			.textoAuditoria(messages.retiraPausaSiacol(protocolo, usuario))
			.create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || retiraPausa", StringUtil.convertObjectToJson(usuario + " - " + protocolo), e);
		}
		
	}

	public void auditaOcultarProtocolo(ProtocoloSiacol protocolo, UserFrontDto usuario) {
		
		try {
			
			service
			.usuario(usuario)
			.modulo(ModuloSistema.SIACOL)
			.dataCriacao(new Date())
			.numero(String.valueOf(protocolo.getNumeroProtocolo()))
			.tipoEvento(TipoEventoAuditoria.INATIVIDADE_ANEXO_APENSO)
			.departamentoDestino(protocolo.getDepartamento().getId())
			.dtoClass(ProtocoloSiacol.class.getSimpleName())
			.dtoNovo(StringUtil.convertObjectToJson(protocolo))
			.textoAuditoria(messages.ocultarProtocolo(protocolo, usuario))
			.create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || auditaOcultarProtocolo", StringUtil.convertObjectToJson(usuario + " - " + protocolo), e);
		}
		
	}

	public void auditaMostrarProtocolo(ProtocoloSiacol protocolo, UserFrontDto usuario) {
		
		try {
			
			service
			.usuario(usuario)
			.modulo(ModuloSistema.SIACOL)
			.dataCriacao(new Date())
			.numero(String.valueOf(protocolo.getNumeroProtocolo()))
			.tipoEvento(TipoEventoAuditoria.RETORNO_INATIVO_ANEXO_APENSO)
			.departamentoDestino(protocolo.getDepartamento().getId())
			.dtoClass(ProtocoloSiacol.class.getSimpleName())
			.dtoNovo(StringUtil.convertObjectToJson(protocolo))
			.textoAuditoria(messages.mostrarProtocolo(protocolo, usuario))
			.create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || auditaMostrarProtocolo", StringUtil.convertObjectToJson(usuario + " - " + protocolo), e);
		}
		
	}

	public void auditaJulgamento(ProtocoloSiacol protocolo, RlDocumentoProtocoloSiacol item, UserFrontDto usuario) {
		
		try {
			
			service
			.usuario(usuario)
			.modulo(ModuloSistema.SIACOL)
			.dataCriacao(new Date())
			.numero(String.valueOf(protocolo.getNumeroProtocolo()))
			.numeroReferencia(protocolo.getNumeroDecisao())
			.tipoEvento(TipoEventoAuditoria.JULGAMENTO_PROTOCOLO)
			.departamentoOrigem(protocolo.getDepartamento().getId())
			.dtoClass(ProtocoloSiacol.class.getSimpleName())
			.dtoNovo(StringUtil.convertObjectToJson(protocolo))
			.textoAuditoria(messages.protocoloJulgado(protocolo, item))
			.create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaSiacolProtocoloFactory || auditaJulgamento", StringUtil.convertObjectToJson(usuario + " - " + item + " - " + protocolo), e);
		}
		
	}
	
}
