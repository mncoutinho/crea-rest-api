package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.NumeroDocumentoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.NumeroDocumentoService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/cadastro/numero-documento")
public class NumeroDocumentoResource {

	@Inject
	ResponseRestApi response;

	@Inject
	NumeroDocumentoService service;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@GET
	@Path("")
	public Response getAll() {
		return response.success().data(service.getAll()).build();
	}
	
	@GET
	@Path("{idDepartamento}")
	public Response getNumeroDocumentoDepartamento(@PathParam("idDepartamento") Long idDepartamento) {
		return response.success().data(service.getNumeroDocumentoDepartamento(idDepartamento)).build();
	}
	
	@GET
	@Path("documento-para-criacao/{idDepartamento}")
	public Response getNumeroDocumentoParaCriacao(@PathParam("idDepartamento") Long idDepartamento) {
		return response.success().data(service.getNumeroDocumentoParaCriacao(idDepartamento)).build();
	}

	@GET
	@Path("{idDepartamento}/{idTipoDocumento}")
	public Response getNumeroDocumento(@PathParam("idDepartamento") Long idDepartamento, @PathParam("idTipoDocumento") Long idTipoDocumento) {
		return response.success().data(service.getNumeroDocumento(idDepartamento, idTipoDocumento)).build();
	}
	
	@GET
	@Path("proximo-numero/{idDepartamento}/{idTipoDocumento}")
	public Response getProximoNumeroDocumento(@PathParam("idDepartamento") Long idDepartamento, @PathParam("idTipoDocumento") Long idTipoDocumento) {
		return response.success().data(service.getProximoNumeroDocumento(idDepartamento, idTipoDocumento)).build();
	}

	@POST
	public Response salvaNumeroDocumento(NumeroDocumentoDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.salvaNumeroDocumento(dto, httpClientGoApi.getUserDto(token))).build();
	}

	
	@PUT
	public Response atualizaNumeroDocumento(NumeroDocumentoDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.atualizaNumeroDocumento(dto, httpClientGoApi.getUserDto(token))).build();
	}
}
