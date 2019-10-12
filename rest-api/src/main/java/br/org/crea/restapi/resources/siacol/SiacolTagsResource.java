package br.org.crea.restapi.resources.siacol;

import java.util.List;

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
import br.org.crea.commons.models.siacol.dtos.SiacolRlProtocoloTagsDto;
import br.org.crea.commons.models.siacol.dtos.SiacolTagsDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.SiacolTagsService;

@Resource
@Path("/siacol/tags")
public class SiacolTagsResource {

	@Inject
	ResponseRestApi response;

	@Inject
	SiacolTagsService siacolTagsService;	

	@GET
	@Publico
	public Response getAll() {
		return response.success().data(siacolTagsService.getAll()).build();
	}
	
	@GET
	@Path("protocolo/{numeroProtocolo}")
	@Publico
	public Response getTagsByProtocolo(@PathParam("numeroProtocolo") Long numeroProtocolo) {
		return response.success().data(siacolTagsService.getTagsByProtocolo(numeroProtocolo)).build();
	}
	
	@POST
	@Publico
	public Response consultarTags(List<SiacolTagsDto> listTagsDto) {
		return response.success().data(siacolTagsService.consultarTags(listTagsDto)).build();
	}

	@POST
	@Path("protocolo")
	@Publico
	public Response salvarTagProtocolo(SiacolRlProtocoloTagsDto siacolRlProtocoloTagsDto) {
		if (siacolTagsService.tagJaFoiCadastrada(siacolRlProtocoloTagsDto)) {
			return response.information().message("Essa Tag já foi cadastrada para esse protocolo!").build();
		}else{
			return response.success().data(siacolTagsService.salvarTagProtocolo(siacolRlProtocoloTagsDto)).build();
		}
	}
	
	@PUT
	@Publico
	public Response atualizarTag(SiacolTagsDto siacolTagDto) {
		if (siacolTagsService.atualizarTag(siacolTagDto) != null) {
			return response.success().message("Tag Atualizada com sucesso!").data(true).build();
		}else {
			return response.information().message("Já Existe uma Tag com essa Descrição").data(false).build();
		}
		
	}
	
	@DELETE
	@Path("protocolo/{idRlProtocoloTags}")
	@Publico
	public Response removerRlProtocoloTags(@PathParam("idRlProtocoloTags") Long idRlProtocoloTags) {
		return response.success().data(siacolTagsService.removerRlProtocoloTags(idRlProtocoloTags))
				.message("Tag deletada com sucesso!").build();
	}

}