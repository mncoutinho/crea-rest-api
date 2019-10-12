package br.org.crea.restapi.resources.corporativo.personalidade;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.corporativo.personalidade.dto.PersonalidadeDto;
import br.org.crea.commons.models.corporativo.personalidade.entity.Personalidade;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.personalidade.PersonalidadeService;

@Resource
@Path("/personalidades")
public class PersonalidadeResource {
	
    @Inject ResponseRestApi response;
	
	@Inject PersonalidadeService service;

	@GET
	@Publico
	public Response getAll(){
		return response.success().data(service.getAll()).build();
	}
	
	@GET
	@Path("{id}")
	@Publico
	public Response getById(@PathParam("id") Long id){
		return response.success().data(service.getById(id)).build();	
	}
	
	@GET
	@Path("by-nomes")
	@Publico
	public Response getByNomes(){
		return response.success().data(service.getByNomes()).build();	
	}
	
	@GET
	@Path("conselheiros/by-nomes")
	@Publico
	public Response getConselheirosByNomes(){
		return response.success().data(service.getConselheirosByNomes()).build();	
	}
	
	@GET
	@Path("presidentes/by-nomes")
	@Publico
	public Response getPresidentesByNomes(){
		return response.success().data(service.getPresidentesByNomes()).build();	
	}
	
	@PUT
	@Publico
	public Response atualizar(PersonalidadeDto dto) {
		return response.success().data(service.atualizar(dto)).build();
	}
	
	@POST
	@Publico
	public Response create(PersonalidadeDto dto) {
		return response.success().data(service.create(dto)).build();
	}
	
}
