package br.org.crea.commons.factory.siacol;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.StringUtil;

public class AuditaSiacolPresencaFactory {

@Inject private AuditoriaService service;
	
	public void auditaAcaoPresenca(Long idReuniao, Long idParticipante, String acao, UserFrontDto usuario) {
		
		ParticipanteReuniaoSiacolDto dto = new ParticipanteReuniaoSiacolDto();
		dto.setIdReuniao(idReuniao);
		dto.setId(idParticipante);
		
	  service
	  .usuario(usuario)
      .modulo(ModuloSistema.SIACOL)
      .dtoClass(ParticipanteReuniaoSiacolDto.class.getSimpleName())
      .dtoNovo(StringUtil.convertObjectToJson(dto))
      .dataCriacao(new Date());
	  
	  auditaAcao(dto, acao);
	  
	}
		
	public void auditaAcao(Object dto, String acao) {
		
		ParticipanteReuniaoSiacolDto participante = (ParticipanteReuniaoSiacolDto) dto;
		TipoEventoAuditoria evento = null; 
		String mensagem = null;
		
		switch (acao) {
		case "ENTRADA":
			evento = TipoEventoAuditoria.SIACOL_PRESENCA_ENTRADA;
			mensagem = "Entrada de " + participante.getId() + " registrada com sucesso";
			break;
		case "SAIDA":
			evento = TipoEventoAuditoria.SIACOL_PRESENCA_SAIDA;
			mensagem = "Saída de " + participante.getId() + " registrada com sucesso";
			break;
		case "DELETA":
			evento = TipoEventoAuditoria.SIACOL_PRESENCA_DELETA;
			mensagem = "Registro de presença de " + participante.getId() + " deletado com sucesso";
			break;
		case "PAUSA":
			evento = TipoEventoAuditoria.SIACOL_PRESENCA_PAUSA;
			mensagem = "Registra presença de " + participante.getId() + " na pausa da reunião " + participante.getIdReuniao();
			break;
		default:
			break;
		}
		
		service
		.tipoEvento(evento)
		.textoAuditoria(mensagem)
		.create();;
	}
}
