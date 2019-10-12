package br.org.crea.restapi.resources.cadastro;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.TituloService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/confeatitulo")

public class TituloResource {

	@Inject ResponseRestApi response;
	
	@Inject HttpClientGoApi httpClientGoApi;
	
	@Inject TituloService service;
	
	@GET
	@Path("list")
	@Publico
	public Response getTitulos() {
		List<DomainGenericDto> listTitulos = service.getAllTitulos();
		return response.success().data(listTitulos).build();
	}
	
	@POST
	@Path("/{idTitulo}")
	@Publico
	public Response getTituloById(@PathParam("idTitulo") Long id) {
		return response.success().data(service.getTituloById(id)).build();
	}
	
}
