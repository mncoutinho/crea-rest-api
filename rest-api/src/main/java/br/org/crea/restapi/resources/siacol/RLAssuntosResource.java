package br.org.crea.restapi.resources.siacol;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.siacol.RlAssuntosSiacol;
import br.org.crea.commons.service.protocolo.RlAssuntosService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/siacol/rl-assunto")
public class RLAssuntosResource {

	@Inject
	ResponseRestApi response;

	@Inject
	RlAssuntosService service;	

	@GET
	@Publico
	public Response getAll() {
		return response.success().data(service.getAll()).build();
	}
	
	@GET
	@Path("assunto/{codigo}")
	@Publico
	public Response getByIdAssuntoSiacol(@PathParam("codigo") Long codigo) {
		return response.success().data(service.getByAssuntoSiacol(codigo)).build();
	}
	

	@POST
	@Publico
	public Response salvar(RlAssuntosSiacol dto) {
		return response.success().data(service.salvar(dto)).build();
	}


}
