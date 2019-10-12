package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.TipoDocumentoDto;
import br.org.crea.commons.service.cadastro.TipoDocumentoService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/tipo-documento")
public class TipoDocumentoResource {
	

	@Inject	ResponseRestApi response;
	
	@Inject TipoDocumentoService service;
	
	
    @GET
    @Publico
    @Path("{modulo}")
    public Response porModulo(@PathParam("modulo") Long modulo){
    	return response.success().data(service.porModulo(modulo)).build();
    }

    @GET
    @Publico
    public Response getAll(){
    	return response.success().data(service.getAll()).build();
    }
	
    @POST
    @Publico
    public Response salva(TipoDocumentoDto dto){
    	return response.success().data(service.salva(dto)).build();
    }
    
    @PUT
    @Publico
    public Response atualiza(TipoDocumentoDto dto){
    	return response.success().data(service.atualiza(dto)).build();
    }
    
	
    
}
