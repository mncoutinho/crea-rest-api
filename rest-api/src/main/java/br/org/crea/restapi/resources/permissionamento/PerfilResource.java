package br.org.crea.restapi.resources.permissionamento;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.permissionamento.dtos.PerfilDto;
import br.org.crea.commons.service.funcionario.PerfilService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/perfil")
public class PerfilResource {
	
	
	@Inject	ResponseRestApi response;

	@Inject	PerfilService service;
	
	
	@GET
	@Path("pessoa/{id}") @Publico
	public Response getPerfilByIdPessoa(@PathParam("id") Long id){
		return response.success().data(service.getPerfilByIdPessoa(id)).build();
	}
	
	
	@POST
	public Response novoPerfil(PerfilDto dto){
		return response.success().data(service.novoPerfil(dto)).build();
	}

	@POST
	@Path("novo") @Publico
	public Response novo(Object dto){
		
		return response.success().data(service.novoPerfil(dto)).build();
	}
	

	@PUT
	@Path("novo/{id}") @Publico
	public Response altera(@PathParam("id") String id, Object obj){
		
		return response.success().data(service.alteraPerfil(id, obj)).build();

	}
	
	
	@POST
	@Path("pessoa-perfil") @Publico
	public Response novo(GenericDto dto){
		return response.success().data(service.salvaPerfil(dto)).build();
	}


}
