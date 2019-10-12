package br.org.crea.restapi.resources.corporativo.personalidade;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.corporativo.personalidade.dto.AjudaCustoDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.personalidade.AjudaCustoService;


@Resource
@Path("/ajuda-custo")
public class AjudaCustoResource {
	
	@Inject ResponseRestApi response;
	
	@Inject AjudaCustoService service;
	
	@GET
	@Publico
	public Response getAll(){
		return response.success().data(service.getAll()).build();
	}
	
    @GET
    @Path("/localidade/{localidade}")
    @Publico
    public Response getByLocalidade(@PathParam("localidade") Long localidade) {
    	return response.success().data(service.getByLocalidade(localidade)).build();
    	
    }
    
	@PUT
	@Publico
	public Response atualizar(AjudaCustoDto dto) {
		return response.success().data(service.atualizar(dto)).build();
	}
    
}
