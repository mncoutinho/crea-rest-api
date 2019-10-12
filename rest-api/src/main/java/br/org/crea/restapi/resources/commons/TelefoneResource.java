package br.org.crea.restapi.resources.commons;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.service.cadastro.TelefoneService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/telefone")
public class TelefoneResource {
	@Inject	ResponseRestApi response;
	
	@Inject TelefoneService service;
	
	@GET
	@Path("pessoa/{idPessoa}") @Publico
	public Response getTelefonesPessoa(@PathParam("idPessoa") Long idPessoa){
		return response.success().data(service.getTelefoneByPessoa(idPessoa)).build();
	}
	
	@POST
	@Publico
	public Response salvar(TelefoneDto dto){
		return response.success().data(service.salvar(dto)).build();
	}
}
