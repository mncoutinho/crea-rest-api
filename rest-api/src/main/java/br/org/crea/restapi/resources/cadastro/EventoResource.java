package br.org.crea.restapi.resources.cadastro;

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
import br.org.crea.commons.models.siacol.dtos.EventoDto;
import br.org.crea.commons.service.cadastro.EventoService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/evento")
public class EventoResource {

	@Inject
	ResponseRestApi response;

	@Inject
	EventoService service;

	@GET
	@Publico
	public Response getAll() {
		return response.success().data(service.getAll()).build();
	}
	
	@GET
	@Path("{idAssuntoProtocolo}") @Publico
	public Response getEventoByAssuntoProtocolo(@PathParam("idAssuntoProtocolo") Long idAssuntoProtocolo) {
		return response.success().data(service.getEventoByAssuntoProtocolo(idAssuntoProtocolo)).build();
	}
	
	@POST
	@Publico
	public Response salvaEvento(EventoDto dto) {
		return response.success().data(service.salvaEvento(dto)).build();		
	}

	@PUT
	@Publico
	public Response atualizaEvento(EventoDto dto) {
		return response.success().data(service.atualizaEvento(dto)).build();
	}
	
	@DELETE
	@Publico
	@Path("{id}")
	public Response deletar(@PathParam("id") Long id) {
		if(service.podeDeletar(id)){
			service.deletar(id);
			return response.success().data("Deletado com sucesso").build();
		}else{
			return response.information().message("Não foi possivel deletar o evento").build();
		}
		
	}


}
