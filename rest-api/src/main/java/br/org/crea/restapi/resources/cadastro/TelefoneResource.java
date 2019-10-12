package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.TelefoneDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.TelefoneService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/cadastro/telefone")
public class TelefoneResource {
	@Inject
	HttpClientGoApi httpClientGoApi;
	
	@Inject	ResponseRestApi response;
	
	@Inject TelefoneService service;

	
	@GET
	@Path("{idPessoa}") @Publico
	public Response getTelefonesBy(@PathParam("idPessoa") Long idPessoa){
		return response.success().data(service.getTelefoneByPessoa(idPessoa)).build();
	}
	
	@POST
	@Publico
	public Response salvaTelefone(TelefoneDto dto){
		return response.success().data(service.salvar(dto)).build();
	}
	
	@PUT
	@Publico
	public Response atualizar(TelefoneDto dto, @HeaderParam("Authorization") String token){
		return response.success().data(service.atualizarTelefone(dto)).build();
	}
	
	@DELETE
	@Publico
	@Path("{codigoTelefone}")
	public Response deletar(@PathParam("codigoTelefone") Long codigoTelefone){
		service.deletarTelefone(codigoTelefone);
		return response.success().build();
	}
	
}
