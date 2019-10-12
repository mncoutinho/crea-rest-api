package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.TaxaService;


@Resource
@Path("/taxa")
public class TaxaResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject TaxaService service;
	

	
	@GET @Publico
	public Response getblocos(){
		return response.success().data(service.getTaxas()).build();
	}
	
	
	@GET  @Publico
	@Path("{idTaxa}")
	public Response getAssuntosDisponiveis(@PathParam("idTaxa") Long idTaxa){
		return response.success().data(service.getTaxaBy(idTaxa)).build();
	}

	
	
	
}
