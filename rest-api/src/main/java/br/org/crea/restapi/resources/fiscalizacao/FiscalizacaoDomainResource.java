package br.org.crea.restapi.resources.fiscalizacao;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.fiscalizacao.FiscalizacaoDomainService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/fiscalizacao")
public class FiscalizacaoDomainResource {

	@Inject
	ResponseRestApi response;

	@Inject
	FiscalizacaoDomainService service;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@GET
	@Path("ramos")
	@Publico
	public Response getRamos() {
		return response.success().data(service.getAllRamos()).build();
	}
	
	@GET
	@Path("atividades/{tipo}")
	@Publico
	public Response getAtividadesPorTipo(@PathParam("tipo") String tipo){
		return response.success().data(service.getAtividades(tipo)).build();
	}
	

}
