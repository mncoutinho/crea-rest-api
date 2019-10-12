package br.org.crea.restapi.resources.commons;

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
import br.org.crea.commons.models.commons.dtos.SituacaoProtocoloDto;
import br.org.crea.commons.service.protocolo.SituacaoProtocoloService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/commons/situacao-protocolo")
public class SituacaoProtocoloResource {
	

	@Inject	ResponseRestApi response;
	
	@Inject SituacaoProtocoloService service;
	
	
    @GET
    @Publico
    public Response getAllSituacoes(){
    	return response.success().data(service.getAllSituacoes()).build();
    }
	
    @GET
    @Path("siacol")
    @Publico
    public Response getSituacaoSiacol(){
    	return response.success().data(service.getSituacaoSiacol()).build();
    }
    
	@GET
	@Path("valida/{codigo}")
	@Publico
	public Response validaCodigoExistente(@PathParam("codigo") Long codigo) {
		return response.success().data(service.existeCodigoExistente(codigo)).build();
	}
	
    @POST
    @Publico
    public Response salvar(SituacaoProtocoloDto dto){
    	return response.success().data(service.salvar(dto)).build();
    }
    
    @PUT
    @Publico
    public Response atualizar(SituacaoProtocoloDto dto){
    	return response.success().data(service.atualizar(dto)).build();
    }
    
    @DELETE
    @Publico
    @Path("{id}")
    public Response deletar(@PathParam("id") Long id){
    	service.deletar(id);
    	return response.success().data("Deletado com sucesso").build();
    }
	

}
