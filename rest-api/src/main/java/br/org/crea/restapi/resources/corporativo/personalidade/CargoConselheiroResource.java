package br.org.crea.restapi.resources.corporativo.personalidade;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.personalidade.CargoConselheiroService;


@Resource
@Path("/cargos-conselheiro")
public class CargoConselheiroResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject CargoConselheiroService service;
	
	@GET
	@Publico
	public Response getAll(){
		return response.success().data(service.getAll()).build();
	}
	
	@GET
	@Path("/id/{id}")
	@Publico
	public Response byId(@PathParam("id") Long id) {
		return response.success().data(service.byId(id)).build();
	}
	
	@GET
	@Path("/cargo/{cargo}")
	@Publico
	public Response getByCargo(@PathParam("cargo") Long cargo) {
		return response.success().data(service.getByCargo(cargo)).build();
	}
	
	@GET
	@Path("countByCargo/{cargo}")
	@Publico
	public Response countByCargo(@PathParam("cargo") Integer cargo) {
		return response.success().data(service.countByCargo(cargo)).build();
	}
	
//	@DELETE
//	@Publico
//	@Path("{id}")
//	public Response deletar(@PathParam("id") Long id) {
//		service.deletar(id);
//		return response.success().data("Deletado com sucesso").build();
//	}

}
