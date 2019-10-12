package br.org.crea.commons.factory;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.commons.validsigner.dto.ValidSignDto;

public class AuditaAssinaturaFactory {
	
	@Inject private AuditoriaService service;
	
	public void auditaAssinaturaDigitalDocumento(ValidSignDto dto, String mensagem, boolean isError) {
		
		service
		  .usuario(dto.getUser())
	      .modulo(dto.getModuloSistema())
	      .isError(isError)
	      .dtoNovo(StringUtil.convertObjectToJson(dto))
	      .dataCriacao(new Date())
		  .textoAuditoria(mensagem)
		  .numero(dto.getIdDocumento().toString())
		  .departamentoOrigem(Long.parseLong(dto.getUnidadeDestino()))
		  .departamentoDestino(Long.parseLong(dto.getUnidadeDestino()))
		  .tipoEvento(TipoEventoAuditoria.ASSINATURA_DIGITAL_DOCUMENTO)
		  .dtoClass(ValidSignDto.class.getSimpleName())
		  .dtoNovo(StringUtil.convertObjectToJson(dto))
		  .create();
	}
}
