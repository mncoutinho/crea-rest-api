package br.org.crea.restapi.resources.siacol;

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
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.AssuntoSiacolService;

@Resource
@Path("/siacol/assunto")
public class AssuntoSiacolResource {

	@Inject
	ResponseRestApi response;

	@Inject
	AssuntoSiacolService service;

	@GET
	@Publico
	public Response getAll() {
		return response.success().data(service.getAll()).build();
	}
	
	@GET
	@Path("ativo")
	@Publico
	public Response getAtivo() {
		return response.success().data(service.getAtivo()).build();
	}
	
	@GET
	@Path("valida/{codigo}")
	@Publico
	public Response validaCodigoAssuntoExistente(@PathParam("codigo") Long codigo) {
		return response.success().data(service.existeCodigoAssunto(codigo)).build();
	}
	
	@POST
	@Publico
	public Response salvar(AssuntoDto dto) {
		return response.success().data(service.salvar(dto)).build();
	}
	
	@POST
	@Publico
	@Path("atribui-confea")
	public Response atribuirConfea(GenericSiacolDto dto) {
		return response.success().data(service.atribuicaoConfea(dto)).build();
	}
	
	@PUT
	@Publico
	public Response atualizar(AssuntoDto dto) {
		return response.success().data(service.atualizar(dto)).build();
	}

	@DELETE
	@Publico
	@Path("{id}")
	public Response deletar(@PathParam("id") Long id) {
		service.deletar(id);
		return response.success().data("Deletado com sucesso").build();
	}

}
