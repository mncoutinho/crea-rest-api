package br.org.crea.commons.factory.commons;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.StringUtil;

public class AuditaDocumentoFactory {

	@Inject private AuditoriaService service;
	
	@Inject private HelperMessages messages;

	@Inject private DepartamentoDao departamentoDao;
	
	@Inject private HttpClientGoApi httpGoApi;
		
	public void auditaCriacao(DocumentoGenericDto dto, UserFrontDto usuario) {
		try {
		  service
		  .usuario(usuario)
	      .modulo(ModuloSistema.SIACOL)
	      .dataCriacao(new Date())
	      .numero(String.valueOf(dto.getProtocolo()))
	      .dtoNovo(StringUtil.convertObjectToJson(dto))
	      .dtoClass(DocumentoGenericDto.class.getSimpleName())
		  .tipoEvento(TipoEventoAuditoria.CADASTRAR_DOCUMENTO)
		  .departamentoOrigem(dto.getDepartamento().getId())
		  .textoAuditoria(messages.criacaoDocumento(dto, usuario, populaNomeDepartamento(dto)))
		  .create();
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaDocumentoFactory || auditaCriacao", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}

	public void auditaAtualizacao(DocumentoGenericDto dto, DocumentoGenericDto dtoAntigo, UserFrontDto usuario) {
		try {
		  service
		  .usuario(usuario)
	      .modulo(ModuloSistema.SIACOL)
	      .dataCriacao(new Date())
	      .numero(String.valueOf(dto.getProtocolo()))
	      .dtoNovo(StringUtil.convertObjectToJson(dto))
	      .dtoClass(DocumentoGenericDto.class.getSimpleName())
	      .dtoAntigo(StringUtil.convertObjectToJson(dtoAntigo))
		  .tipoEvento(TipoEventoAuditoria.ATUALIZAR_DOCUMENTO)
		  .departamentoOrigem(dto.getDepartamento().getId())
		  .textoAuditoria(messages.atualizaDocumento(dto, usuario, populaNomeDepartamento(dto)))
		  .create();
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaDocumentoFactory || auditaAtualizacao", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}
	
	public void auditaExclusao(DocumentoGenericDto dto, UserFrontDto usuario) {
		try {
		  service
		  .usuario(usuario)
	      .modulo(ModuloSistema.SIACOL)
	      .dataCriacao(new Date())
	      .numero(String.valueOf(dto.getProtocolo()))
	      .dtoAntigo(StringUtil.convertObjectToJson(dto))
	      .dtoClass(DocumentoGenericDto.class.getSimpleName())
		  .tipoEvento(TipoEventoAuditoria.EXCLUIR_DOCUMENTO)
		  .departamentoOrigem(dto.getDepartamento().getId())
		  .textoAuditoria(messages.exclusaoDocumento(dto, usuario, populaNomeDepartamento(dto)))
		  .create();
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaDocumentoFactory || auditaExclusao", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}

	public void auditaAssinatura(DocumentoGenericDto dto, UserFrontDto usuario) {
		try {
		  service
		  .usuario(usuario)
	      .modulo(ModuloSistema.SIACOL)
	      .dataCriacao(new Date())
	      .numero(String.valueOf(dto.getProtocolo()))
	      .dtoNovo(StringUtil.convertObjectToJson(dto))
	      .dtoClass(DocumentoGenericDto.class.getSimpleName())
		  .tipoEvento(TipoEventoAuditoria.ASSINATURA_DIGITAL_DOCUMENTO)
		  .departamentoOrigem(dto.getDepartamento().getId())
		  .textoAuditoria(messages.assinaDocumento(dto, usuario, populaNomeDepartamento(dto)))
		  .create();
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaDocumentoFactory || auditaAssinatura", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}
	
	private String populaNomeDepartamento(DocumentoGenericDto dto) {
		String nomeDepartamento = "";
		if (dto.temDepartamento()) {
			Departamento departamento = departamentoDao.buscaDepartamentoPor(dto.getDepartamento().getId());
			
			nomeDepartamento = departamento != null ? departamento.getNome() : "";
		}
		
		return nomeDepartamento;
	}
}
