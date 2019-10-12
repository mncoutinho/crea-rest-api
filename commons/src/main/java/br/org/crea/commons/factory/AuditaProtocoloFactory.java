package br.org.crea.commons.factory;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.SubstituicaoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.StringUtil;

public class AuditaProtocoloFactory {

	@Inject private AuditoriaService service;
	
	@Inject private HelperMessages helperMessages;
	
	@Inject private HttpClientGoApi httpGoApi;
	
	public void auditaAcaoProtocolo(Object dto, UserFrontDto usuario, String mensagem, ModuloSistema modulo, boolean isError) {
		
		try {
		  service
		  .usuario(usuario)
	      .modulo(modulo)
	      .isError(isError)
	      .dtoNovo(StringUtil.convertObjectToJson(dto))
	      .dataCriacao(new Date())
		  .textoAuditoria(mensagem);
			
		  if (dto.getClass().equals(TramiteDto.class)) {
			  auditaEnvioOrRecebimento(dto);
		  }
			  
		  if (dto.getClass().equals(SubstituicaoProtocoloDto.class)) {
			  auditaSubstituicao(dto);
		  }
		  
		  if (dto.getClass().equals(ProtocoloSiacolDto.class)) {
			  auditaAtualizaçãoProtocolo(dto);
		  }
		  
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaProtocoloFactory || auditaAcaoProtocolo", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	  
	}

	public void auditaEnvioOrRecebimento(Object dto) {
		try {
			TramiteDto tramite = (TramiteDto) dto;
			
			TipoEventoAuditoria evento = tramite.getIdDepartamentoDestino() != null ? 
					TipoEventoAuditoria.TRAMITAR_PROTOCOLO : TipoEventoAuditoria.RECEBER_PROTOCOLO;  
			
			service
		    .numero(String.valueOf(tramite.getNumeroProtocolo()))
		    .departamentoOrigem(tramite.getUltimoMovimento().getIdDepartamentoDestino())
		    .departamentoDestino(tramite.getIdDepartamentoDestino())
		    .tipoEvento(evento)
		    .dtoClass(TramiteDto.class.getSimpleName())
		    .dtoNovo(StringUtil.convertObjectToJson(dto))
		    .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaProtocoloFactory || auditaEnvioOrRecebimento", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public void auditaEnvioOrRecebimentoDocflow(Object dto, UserFrontDto usuario, ModuloSistema modulo, boolean isError) {
		try {
			TramiteDto tramite = (TramiteDto) dto;
			
			TipoEventoAuditoria evento = tramite.getIdDepartamentoDestino() != null ? 
					TipoEventoAuditoria.TRAMITAR_PROTOCOLO_DOCFLOW : TipoEventoAuditoria.RECEBER_PROTOCOLO_DOCFLOW;  
			
			service
			  .usuario(usuario)
		      .modulo(modulo)
		      .isError(isError)
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dataCriacao(new Date())
			  .textoAuditoria(helperMessages.auditaEnvioOuRecebimentoDocflow(tramite, evento))
			  .numero(String.valueOf(tramite.getNumeroProtocolo()))
			  .departamentoOrigem(tramite.getUltimoMovimento().getIdDepartamentoDestino())
			  .departamentoDestino(tramite.getIdDepartamentoDestino())
			  .tipoEvento(evento)
			  .dtoClass(TramiteDto.class.getSimpleName())
			  .dtoNovo(StringUtil.convertObjectToJson(dto))
			  .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaProtocoloFactory || auditaEnvioOrRecebimentoDocflow", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}
	
	public void auditaEnvioRecebimentoAnexo(TramiteDto tramite, ProtocoloSiacol dto, UserFrontDto usuario, ModuloSistema modulo) {
		
		try {
			
			TipoEventoAuditoria evento = tramite.getIdDepartamentoDestino() != null ? 
					TipoEventoAuditoria.TRAMITAR_PROTOCOLO_ANEXO : TipoEventoAuditoria.RECEBER_PROTOCOLO_ANEXO;  
			
			service
			  .usuario(usuario)
		      .modulo(modulo)
		      .isError(tramite.isPossuiErros())
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dataCriacao(new Date())
			  .textoAuditoria(helperMessages.auditaMovimentoAnexo(tramite, dto, evento))
			  .numero(String.valueOf(dto.getNumeroProtocolo()))
			  .numeroReferencia(String.valueOf(tramite.getNumeroProtocolo()))
		      .departamentoOrigem(tramite.getUltimoMovimento().getIdDepartamentoDestino())
		      .departamentoDestino(tramite.getIdDepartamentoDestino())
		      .tipoEvento(evento)
		      .dtoClass(TramiteDto.class.getSimpleName())
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaProtocoloFactory || auditaEnvioRecebimentoAnexo", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}
	
	public void auditaEnvioRecebimentoApenso(TramiteDto tramite, ProtocoloSiacol dto, UserFrontDto usuario, ModuloSistema modulo) {
		try {
			TipoEventoAuditoria evento = tramite.getIdDepartamentoDestino() != null ? 
					TipoEventoAuditoria.TRAMITAR_PROTOCOLO_APENSO : TipoEventoAuditoria.RECEBER_PROTOCOLO_APENSO;  
			
			service
			  .usuario(usuario)
		      .modulo(modulo)
		      .isError(tramite.isPossuiErros())
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dataCriacao(new Date())
			  .textoAuditoria(helperMessages.auditaMovimentoApenso(tramite, dto, evento))
			  .numero(String.valueOf(dto.getNumeroProtocolo()))
			  .numeroReferencia(String.valueOf(tramite.getNumeroProtocolo()))
		      .departamentoOrigem(tramite.getUltimoMovimento().getIdDepartamentoDestino())
		      .departamentoDestino(tramite.getIdDepartamentoDestino())
		      .tipoEvento(evento)
		      .dtoClass(TramiteDto.class.getSimpleName())
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaProtocoloFactory || auditaEnvioRecebimentoApenso", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}
	
	
	public void auditaSubstituicao(Object dto) {
		try {
	
			SubstituicaoProtocoloDto substituicao = (SubstituicaoProtocoloDto) dto;
			
			service
		    .numero(String.valueOf(substituicao.getProtocoloSubstituido().getNumeroProtocolo()))
		    .departamentoOrigem(substituicao.getProtocoloSubstituido().getUltimoMovimento().getIdDepartamentoOrigem())
		    .departamentoDestino(substituicao.getProtocoloSubstituido().getUltimoMovimento().getIdDepartamentoDestino())
		    .tipoEvento(TipoEventoAuditoria.SUBSTITUIR_PROTOCOLO)
		    .dtoClass(SubstituicaoProtocoloDto.class.getSimpleName())
		    .dtoNovo(StringUtil.convertObjectToJson(dto))
		    .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaProtocoloFactory || auditaSubstituicao", StringUtil.convertObjectToJson(dto), e);
		}
		
	}
	
	public void auditaJuntada(JuntadaProtocoloDto dto, UserFrontDto usuario) {
		try {
			JuntadaProtocoloDto juntada = (JuntadaProtocoloDto) dto;
			TipoEventoAuditoria evento = null;
			String acaoEmissor = null;
			String acaoDestinatario = null;
			
			switch (juntada.getTipoJuntadaProtocolo()) {
			case ANEXACAO:
				evento = TipoEventoAuditoria.ANEXAR_PROTOCOLO;
				acaoEmissor = " foi anexado ao protocolo ";
				acaoDestinatario = " ganhou o anexo ";
				break;
			case APENSACAO:
				evento = TipoEventoAuditoria.APENSAR_PROTOCOLO;
				acaoEmissor = " foi apensado ao protocolo ";
				acaoDestinatario = " ganhou o apenso ";
				break;
			case DESANEXACAO:
				evento = TipoEventoAuditoria.DESANEXAR_PROTOCOLO;
				acaoEmissor = " foi desanexado ao protocolo ";
				acaoDestinatario = " perdeu o anexo ";
				break;
			case DESAPENSACAO:
				evento = TipoEventoAuditoria.DESAPENSAR_PROTOCOLO;
				acaoEmissor = " foi desapensado ao protocolo ";
				acaoDestinatario = " perdeu o apenso ";
				break;
			default:
				break;
			}
			auditaEventoJuntada(juntada, evento, usuario, acaoEmissor, acaoDestinatario); 
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaProtocoloFactory || auditaJuntada", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}
	
	public void auditaEventoJuntada(JuntadaProtocoloDto dto, TipoEventoAuditoria evento, UserFrontDto usuario, String acaoEmissor, String acaoDestinatario) {
		try {
			
			service
			  .usuario(usuario)
		      .modulo(dto.getModulo())
		      .isError(dto.possuiErrosNaJuntada())
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dataCriacao(new Date())
			  .textoAuditoria(helperMessages.anexarProtocolo(dto, usuario, acaoEmissor))
			  .numero(String.valueOf(dto.getProtocoloDaJuntada().getNumeroProtocolo()))
			  .departamentoOrigem(dto.getProtocoloDaJuntada().getUltimoMovimento().getIdDepartamentoOrigem())
			  .departamentoDestino(dto.getProtocoloDaJuntada().getUltimoMovimento().getIdDepartamentoDestino())
			  .tipoEvento(evento)
			  .dtoClass(dto.getClass().getName())
			  .dtoNovo(StringUtil.convertObjectToJson(dto))
			  .create();
			
			service
			  .usuario(usuario)
		      .modulo(dto.getModulo())
		      .isError(dto.possuiErrosNaJuntada())
		      .dtoNovo(StringUtil.convertObjectToJson(dto))
		      .dataCriacao(new Date())
			  .textoAuditoria(helperMessages.receberAnexoProtocolo(dto, usuario, acaoDestinatario))
			  .numero(String.valueOf(dto.getProtocoloPrincipal().getNumeroProtocolo()))
			  .departamentoOrigem(dto.getProtocoloPrincipal().getUltimoMovimento().getIdDepartamentoOrigem())
			  .departamentoDestino(dto.getProtocoloPrincipal().getUltimoMovimento().getIdDepartamentoDestino())
			  .tipoEvento(evento)
			  .dtoClass(dto.getClass().getName())
			  .dtoNovo(StringUtil.convertObjectToJson(dto))
			  .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaProtocoloFactory || auditaEventoJuntada", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}
	
	
	private void auditaAtualizaçãoProtocolo(Object dto) {
		try {
			ProtocoloSiacolDto protocolo = (ProtocoloSiacolDto) dto;
			
			service
		    .numero(String.valueOf(protocolo.getNumeroProtocolo()))
		    .tipoEvento(protocolo.getTipoEventoAuditoria())
		    .dtoClass(protocolo.getClass().getName())
		    .dtoNovo(StringUtil.convertObjectToJson(protocolo))
		    .create();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaProtocoloFactory || auditaAtualizaçãoProtocolo", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
}
