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
import br.org.crea.commons.models.siacol.dtos.ParteTextoDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.ParteTextoService;

@Resource
@Path("/siacol/parte-texto")
public class ParteTextoResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject ParteTextoService service;
	
    @GET
    @Publico
    public Response getAllTextos(){
    	return response.success().data(service.getAllTextos()).build();
    }
	
    @POST
    @Publico
    public Response salva(ParteTextoDto dto){
    	return response.success().data(service.salva(dto)).build();
    }
    
    @PUT
    @Publico
    public Response atualiza(ParteTextoDto dto){
    	return response.success().data(service.atualiza(dto)).build();
    }
    
    @DELETE
    @Publico
    @Path("{id}")
    public Response deleta(@PathParam("id") Long id){
    	return response.success().data(service.deleta(id)).build();
    }
}
