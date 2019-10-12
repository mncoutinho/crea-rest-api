package br.org.crea.restapi.resources.siacol;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.PresencaReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.StatusPresencaReuniaoEnum;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.PersonalidadeSiacolService;
import br.org.crea.siacol.service.PresencaReuniaoSiacolService;
import br.org.crea.siacol.service.VotoReuniaoSiacolService;

@Resource
@Path("siacol/reuniao/presenca")
public class PresencaReuniaoSiacolResource {
	
	@Inject ResponseRestApi response;
	
	@Inject PresencaReuniaoSiacolService service;
	
	@Inject PersonalidadeSiacolService personalidadeService;
	
	@Inject VotoReuniaoSiacolService votoService;
	
	@POST
	@Path("atualiza/{acao}") @Publico
	public Response atualizaPresenca(@PathParam("acao") String acao, PresencaReuniaoSiacolDto dto){
		dto.setAcao(acao);
		service.atualizaPresenca(dto);
		return response.success().build();
	}

	@POST
	@Path("registra") @Publico
	public Response registraPresenca(ParticipanteReuniaoSiacolDto dto){
		service.registraPresenca(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("atualiza-voto-minerva") @Publico
	public Response atualizaMesaDiretoraCamara(ParticipanteReuniaoSiacolDto dto){
		service.atualizaMesaDiretoraCamra(dto);
		return response.success().build();
	}

	@GET
	@Path("{idReuniao}") @Publico
	public Response presentes(@PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.presentes(idReuniao)).build();
	}
	
	@POST
	@Path("entrega-cracha-automatica/{idReuniao}/{cracha}") @Publico
	public Response entregaCrachaAutomatica(@PathParam("idReuniao") Long idReuniao, @PathParam("cracha") Long cracha){
		
		PesquisaGenericDto pesquisa = new PesquisaGenericDto();
		pesquisa.setTipo("CRACHA");
		pesquisa.setRegistro(cracha.toString());
		pesquisa.setIdDocumento(idReuniao);
		
		ParticipanteReuniaoSiacolDto participanteDto = personalidadeService.getConselheiroPor(pesquisa);
		
		if (!participanteDto.getStatusPresenca().equals(StatusPresencaReuniaoEnum.X)) {
			
			return response.information().message("Já foi registrada a entrega do Crachá de " + participanteDto.getNome()).build();
		} else {
			
			if (!participanteDto.getStatusPresencaEfetivoOuSuplente().equals(StatusPresencaReuniaoEnum.X)) {
				if (participanteDto.getEhSuplente()) {
					return response.information().message("Efetivo já registrou presença na reunião!").build();
				} else {
					return response.information().message("Suplente já registrou presença na reunião!").build();
				}
			} else {
				service.entregaCrachaAutomatica(idReuniao, participanteDto.getId());
				participanteDto.setStatusPresenca(StatusPresencaReuniaoEnum.E);
				return response.success().data(participanteDto).build();
			}
		}
	}
	
	@PUT
	@Path("devolucao-cracha-automatica/{idReuniao}/{cracha}") @Publico	
	public Response devolucaoCrachaAutomatica(@PathParam("idReuniao") Long idReuniao, @PathParam("cracha") Long cracha){
		
		PesquisaGenericDto pesquisa = new PesquisaGenericDto();
		pesquisa.setTipo("CRACHA");
		pesquisa.setRegistro(cracha.toString());
		pesquisa.setIdDocumento(idReuniao);
		
		ParticipanteReuniaoSiacolDto participanteDto = personalidadeService.getConselheiroPor(pesquisa);
		
		if (participanteDto.getStatusPresenca().equals(StatusPresencaReuniaoEnum.D))
			return response.information().message("Já foi registrada a devolução do crachá de " + participanteDto.getNome()).build();
		else if (participanteDto.getStatusPresenca().equals(StatusPresencaReuniaoEnum.X)) {
			return response.information().message("Não é possível registrar a devolução do crachá, não há registro de entrega").build();
		} else {
			service.devolucaoCrachaAutomatica(idReuniao, participanteDto.getId());
			return response.success().data(participanteDto).build();
		}
	}
	
	@DELETE
	@Path("remove-registro-presenca/{idReuniao}/{idParticipante}") @Publico
	public Response removeRegistroPresenca(@PathParam("idReuniao") Long idReuniao, @PathParam("idParticipante") Long idParticipante){
		if (votoService.jaVotou(idReuniao, idParticipante)) {
			return response.information().build();
		} else {		
			service.removeRegistroPresenca(idReuniao, idParticipante);
			return response.success().build();
		}
	}
	
}
