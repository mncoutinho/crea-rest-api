package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.DocumentacaoService;


@Resource
@Path("/documento")
public class DocumentacaoResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject DocumentacaoService documentacaoService;
	
	
	@GET 
	@Path("disponiveis/{idAssunto}") @Publico
	public Response getDocumentacaoDisponiveis(@PathParam("idAssunto") Long idAssunto){
		return response.success().data(documentacaoService.getDocumentacaoDisponiveis(idAssunto)).build();
	}
	
	
	@GET 
	@Path("indisponiveis/{idAssunto}") @Publico
	public Response getDocumentacaoIndisponiveis(@PathParam("idAssunto") Long idAssunto){
		return response.success().data(documentacaoService.getDocumentacaoIndisponiveis(idAssunto)).build();
	}
	
	
	
}
