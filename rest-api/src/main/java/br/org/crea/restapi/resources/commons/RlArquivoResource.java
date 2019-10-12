package br.org.crea.restapi.resources.commons;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.RlArquivoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.commons.RlArquivoService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/commons/rl-arquivo")
public class RlArquivoResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject RlArquivoService service;
	
	@Inject HttpClientGoApi httpClientGoApi;

	@POST
	public Response criarRlArquivo(RlArquivoDto dto) {
		return response.success().data(service.criarRlArquivo(dto)).build();
	}
	
	@POST 
	@Path("/tipo/id") @Publico
	public Response getRlByTipoId(RlArquivoDto dto) {
		return response.success().data(service.getRlByTipoId(dto)).build();
	}	
	
//	@DELETE
//	@Path("{id}")
//	public Response delete(@PathParam("id") Long id) {
//		return response.success().data(service.delete(id)).build();
//	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) {
		return response.success().data(service.delete(id)).build();
	}
	
}
