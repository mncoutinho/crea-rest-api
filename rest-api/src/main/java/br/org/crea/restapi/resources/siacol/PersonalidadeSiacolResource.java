package br.org.crea.restapi.resources.siacol;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.siacol.dtos.AuthPainelSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.PersonalidadeSiacolService;

@Resource
@Path("siacol/personalidade")
public class PersonalidadeSiacolResource {
	
	@Inject 
	ResponseRestApi response;
	
	@Inject
	PersonalidadeSiacolService service;

	@POST
	@Path("autentica")
	@Publico
	public Response auth(AuthPainelSiacolDto dto) {
		
		if (service.estaAbertaParaVotacao(dto.getIdReuniao())) {
			
			ParticipanteReuniaoSiacolDto participanteDto = service.autentica(dto);
			if (participanteDto != null) {
				if (service.estaPresente(participanteDto.getId(), dto.getIdReuniao())) {
					return response.success().data(participanteDto).build();
				} else {
					return response.information().data("Não está habilitado para participar desta reunião").build();
				}					 
			} else {
				return response.information().data("Senha inválida").build();
			}			
		} else {
			return response.information().data("Reunião não está aberta").build();
		}		
	}
	
	/** Método para buscar os conselheiros na recepção, atendendo as reuniões de câmara
	 * @param idDepartamento - codigo do departamento
	 * @param idReuniao - codigo da reunião
	 */
	@GET
	@Path("busca-conselheiros/{idDepartamento}/{idReuniao}") @Publico
	public Response getConselheirosPorIdDepartamento(@PathParam("idDepartamento") Long idDepartamento, @PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.getConselheirosPorIdDepartamento(idDepartamento, idReuniao)).build();
	}
		
	/** Método para buscar os conselheiros na recepção, atendendo as reuniões de comissões, 
	 *  neste caso os suplentes não estão relacionados a conselheiros, mas apenas ao departamento 
	 * @param idDepartamento - codigo do departamento
	 * @param idReuniao - codigo da reunião
	 */
	@GET
	@Path("busca-conselheiros-comissoes/{idDepartamento}/{idReuniao}") @Publico
	public Response getConselheirosPorIdComissoes(@PathParam("idDepartamento") Long idDepartamento, @PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.getConselheirosPorIdComissoes(idDepartamento, idReuniao)).build();
	}
	
	@POST
	@Path("busca-conselheiro-plenaria") @Publico
	public Response getConselheiroPor(PesquisaGenericDto dto){
		return response.success().data(service.getConselheiroPor(dto)).build();
	}
	
	@GET
	@Path("busca-com-presenca/{idReuniao}") @Publico
	public Response getConselheirosComPresencaNaReuniao(@PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.getConselheirosComPresencaNaReuniao(idReuniao)).build();
	}
	
	@GET
	@Path("busca-presentes-aclamacao/{idReuniao}") @Publico
	public Response getConselheirosComPresentesSemMesaDiretora(@PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.getConselheirosComPresentesSemMesaDiretora(idReuniao)).build();
	}

	@GET
	@Path("busca-ausentes/{idDepartamento}/{idReuniao}") @Publico
	public Response getConselheirosAusentesPorIdDepartamento(@PathParam("idDepartamento") Long idDepartamento, @PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.getConselheirosAusentesPorIdDepartamento(idDepartamento, idReuniao)).build();
	}
	
	@GET
	@Path("busca-licenciados/{idDepartamento}") @Publico
	public Response getConselheirosLicenciadosPorIdDepartamento(@PathParam("idDepartamento") Long idDepartamento){
		return response.success().data(service.getConselheirosLicenciadosPorIdDepartamento(idDepartamento)).build();
	}
	
	@PUT
	@Path("redefine-senha") @Publico
	public Response redefineSenha(ParticipanteReuniaoSiacolDto participante){
		return service.redefineSenha(participante) ? response.success().build() : response.information().build();
	}
	
	@PUT
	@Path("atualiza-nome-guerra") @Publico
	public Response atualizaNomeGuerra(ParticipanteReuniaoSiacolDto participante){
		service.atualizaNomeGuerra(participante);
		return response.success().build();
	}
		
	@GET
	@Path("busca-diretoria/{idReuniao}") @Publico
	public Response getDiretoria(@PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.getDiretoria(idReuniao)).build();
	}
	
	@GET
	@Path("busca-funcionario-mesa-diretora/{idReuniao}") @Publico
	public Response getFuncionarioMesaDiretora(@PathParam("idReuniao") Long idReuniao){
		return response.success().data(service.getFuncionarioMesaDiretora(idReuniao)).build();
	}
	
	@POST
	@Path("cadastra-mesa-diretora/{idReuniao}") @Publico
	public Response cadastraMesaDiretora(@PathParam("idReuniao") Long idReuniao, List<ParticipanteReuniaoSiacolDto> ListaParticipantesMesaDiretora){
		service.cadastraMesaDiretora(idReuniao, ListaParticipantesMesaDiretora);
		return response.success().build();
	}
	
	@GET
	@Path("busca-departamento-conselheiro/{idConselheiro}") @Publico
	public Response getDepartamentoByIdConselheiro(@PathParam("idConselheiro") Long idConselheiro){
		return response.success().data(service.getDepartamentoByIdConselheiro(idConselheiro)).build();
	}
	
	@GET
	@Path("minerva/{idReuniao}") @Publico
	public Response getMinerva(@PathParam("idReuniao") Long idReuniao){
		ParticipanteReuniaoSiacolDto dto = service.getMinerva(idReuniao);
		
		if (dto != null) {
			return response.success().data(dto).build();
		} else {
			return response.information().build();
		}
		
	}
}
