package br.org.crea.commons.factory.commons;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.helper.HelperMessages;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.StringUtil;

public class AuditaDocflowFactory {

	@Inject private AuditoriaService service;
	
	@Inject private HelperMessages messages;
	
	@Inject private DepartamentoDao departamentoDao;
	
	@Inject private HttpClientGoApi httpGoApi;
		
	public void cadastrarDocumento(DocflowGenericDto dto, UserFrontDto usuario) {
		try {
		  service
		  .usuario(usuario)
	      .modulo(ModuloSistema.SIACOL)
	      .dataCriacao(new Date())
	      .numero(String.valueOf(dto.getProtocoloDoDocumento()))
	      .dtoNovo(StringUtil.convertObjectToJson(dto))
	      .dtoClass(DocumentoGenericDto.class.getSimpleName())
		  .tipoEvento(TipoEventoAuditoria.DOCFLOW_CADASTRAR_DOCUMENTO)
		  .departamentoOrigem(Long.valueOf(dto.getCodigoDepartamento()))
		  .textoAuditoria(messages.cadastrarDocumentoDocflow(dto, usuario, populaNomeDepartamento(dto)))
		  .create();
		  
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditaDocflowFactory || cadastrarDocumento", StringUtil.convertObjectToJson(dto + " - " + usuario), e);
		}
	}
	
	private String populaNomeDepartamento(DocflowGenericDto dto) {
		String nomeDepartamento = "";
		if (dto.getUnidadeOrigem() != null) {
			Departamento departamento = departamentoDao.buscaDepartamentoPor(Long.parseLong(dto.getUnidadeOrigem()));
			
			nomeDepartamento = departamento != null ? departamento.getNome() : "";
		}
		
		return nomeDepartamento;
	}
}
