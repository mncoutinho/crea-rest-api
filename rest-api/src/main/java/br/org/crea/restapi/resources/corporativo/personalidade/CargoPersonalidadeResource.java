package br.org.crea.restapi.resources.corporativo.personalidade;

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
import br.org.crea.commons.models.corporativo.personalidade.dto.CargoPersonalidadeDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.personalidade.CargoPersonalidadeService;

@Resource
@Path("/cargos-personalidade")
public class CargoPersonalidadeResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject CargoPersonalidadeService service;
	
	@GET
	@Publico
	public Response getAll(){
		return response.success().data(service.getAll()).build();
	}
	
	@PUT
	@Publico
	public Response atualizar(CargoPersonalidadeDto dto) {
		return response.success().data(service.atualizar(dto)).build();
	}
		
	@GET
	@Path("{codigo}")
	@Publico
	public Response getByCodigo(@PathParam("codigo") Long codigo) {
		return response.success().data(service.getByCodigo(codigo)).build();
	}
	
	@POST
	@Publico
	public Response salvar(CargoPersonalidadeDto dto) {
		return response.success().data(service.salvar(dto)).build();
	}
	
	@DELETE
	@Publico
	@Path("{id}")
	public Response deletar(@PathParam("id") Long id) {
		service.deletar(id);
		return response.success().data("Deletado com sucesso").build();
	}

	
}
