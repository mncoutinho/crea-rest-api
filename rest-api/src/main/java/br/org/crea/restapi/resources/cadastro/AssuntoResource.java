package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.service.protocolo.AssuntoService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/assunto")
public class AssuntoResource {

	@Inject
	ResponseRestApi response;

	@Inject
	AssuntoService service;


	@GET
	@Path("blocos/")
	@Publico
	public Response getblocos() {
		return response.success().data(service.getblocos()).build();
	}

	@GET
	@Path("disponiveis/{idBloco}")
	public Response getAssuntosDisponiveis(@PathParam("idBloco") Long idBloco) {
		return response.success().data(service.getAssuntosDisponiveis(idBloco)).build();
	}

	@GET
	@Path("com_documentacao/")
	public Response getAssuntosComDocumentacao() {
		return response.success().data(service.getAssuntosComDocumentacao()).build();
	}
	
	@GET
	@Publico
	public Response getAssuntos() {
		return response.success().data(service.getAssuntos()).build();
	}
	
	@POST
	@Path("atualiza")
	@Publico
	public Response atualizaAssunto(AssuntoDto dto) {
		return response.success().data(service.atualizaAssunto(dto)).build();
	}
	
	@GET
	@Path("siacol")
	@Publico
	public Response getAssuntoSiacol() {
		return response.success().data(service.getAssuntoSiacol()).build();
	}
	
	@GET
	@Path("tipo-assunto/{tipoAssunto}")
	@Publico
	public Response getAssuntoByTipoAssunto(@PathParam("tipoAssunto") String tipoAssunto) {
		return response.success().data(service.getAssuntoByTipoAssunto(tipoAssunto)).build();
	}
		
	@POST
	@Path("siacol/habilitado")
	@Publico
	public Response getAssuntoSiacolHabilitadoFuncionario(FuncionarioDto dto) {
		return response.success().data(service.getAssuntoSiacolHabilitadoFuncionario(dto)).build();
	}
	
	@POST
	@Path("siacol/desabilitado")
	@Publico
	public Response getAssuntoSiacolDesabilitadoFuncionario(FuncionarioDto dto) {
		return response.success().data(service.getAssuntoSiacolDesabilitadoFuncionario(dto)).build();
	}
	
	@POST
	@Path("siacol/habilita-assunto")
	@Publico
	public Response habilitaSiacol(AssuntoDto dto) {
		return response.success().data(service.habilitaSiacol(dto)).build();
	}

}
